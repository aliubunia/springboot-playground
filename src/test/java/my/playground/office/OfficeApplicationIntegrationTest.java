package my.playground.office;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

import my.playground.office.model.Office;
import my.playground.office.util.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static java.util.Arrays.asList;
import static my.playground.office.util.JsonUtils.stream;
import static org.junit.Assert.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest("server.port:0") @WebAppConfiguration
@SpringApplicationConfiguration(classes = OfficeApplication.class)
public class OfficeApplicationIntegrationTest {

    @Value("${local.server.port}")
    int serverPort;

    @Inject
    CrudRepository<Office, Integer> repository;

    TestRestTemplate template = new TestRestTemplate();
    List<Integer> officesToCleanup = new ArrayList<>();

    @After
    public void tearDown() {
        officesToCleanup.stream().forEach(id -> repository.delete(id));
        officesToCleanup.clear();
    }

    @Test
    public void testSaveAndLoadOffice() throws Exception {

        JSONObject newOffice = new JSONObject();
        newOffice.put("country", "NL");
        newOffice.put("city", "Den Haag");
        newOffice.put("openFrom", "10:00:00+02:00");
        newOffice.put("openUntil", "17:30:00+02:00");

        ResponseEntity<String> response = template.exchange(createPostRequest(newOffice, "/api/office/save.json"), String.class);

        assertEquals(CREATED, response.getStatusCode());

        JSONObject savedOffice = new JSONObject(response.getBody());
        assertTrue(savedOffice.has("id"));
        officesToCleanup.add(savedOffice.getInt("id"));


        URI location = response.getHeaders().getLocation();
        assertNotNull(location);

        response = template.getForEntity(resourceURL(location.getPath()), String.class);

        assertEquals(OK, response.getStatusCode());

        JSONObject loadedOffice = new JSONObject(response.getBody());
        assertEqualOffices(newOffice, loadedOffice);
    }

    @Test
    public void testLoadAllOffices() {

        ResponseEntity<String> response = template.getForEntity(resourceURL("/api/office/all.json"), String.class);

        assertEquals(OK, response.getStatusCode());

        JSONArray jsonArray = new JSONArray(response.getBody());
        List<String> countries = stream(jsonArray).
                map(office -> office.getString("country")).
                sorted().
                collect(Collectors.toList());
        assertEquals(asList("AU", "BR", "DE"), countries);
    }

    @Test
    public void testLoadOfficesOpenNow() {

        ResponseEntity<String> response = template.getForEntity(resourceURL("/api/office/openNow.json"), String.class);

        assertEquals(OK, response.getStatusCode());

        JSONArray jsonArray = new JSONArray(response.getBody());
        List<String> countries = stream(jsonArray).
                map(office -> office.getString("country")).
                sorted().
                collect(Collectors.toList());

        assertTrue(asList("AU", "BR", "DE").containsAll(countries));
    }

    private String resourceURL(String resource) {
        return "http://localhost:" + serverPort + resource;
    }

    private RequestEntity<String> createPostRequest(JSONObject json, String location) throws URISyntaxException {
        return RequestEntity.post(new URI(resourceURL(location))).
                contentType(APPLICATION_JSON).
                body(json.toString());
    }

    private void assertEqualOffices(JSONObject expectedOffice, JSONObject actualOffice) {
        JSONObject office1 = new JSONObject(expectedOffice);
        office1.remove("id");
        JSONObject office2 = new JSONObject(actualOffice);
        office2.remove("id");
        assertTrue(office1.similar(office2));
    }

}
