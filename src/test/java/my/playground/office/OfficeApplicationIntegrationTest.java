package my.playground.office;


import my.playground.office.endpoints.JsonOffice;
import my.playground.office.model.Office;
import my.playground.office.services.Clock;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.springframework.http.HttpStatus.CREATED;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest("server.port:0") @WebAppConfiguration
@SpringApplicationConfiguration(classes = OfficeApplication.class)
public class OfficeApplicationIntegrationTest {

    @Value("${local.server.port}")
    int serverPort;

    @Inject
    Clock clock;

    @Inject
    List<HttpMessageConverter<?>> jsonConverters;

    @Inject
    CrudRepository<Office, Integer> repository;

    List<Office> officesToCleanup = new ArrayList<>();

    @After
    public void tearDown() {
        for (Office office : officesToCleanup) {
            if (office.getId() != null) {
                repository.delete(office.getId());
            }
        }
        officesToCleanup.clear();
    }


    @Test
    public void testSave() {

        Office newOffice = new Office();
        newOffice.setCountry("NL");
        newOffice.setCity("Den Haag");
        newOffice.setOpenFrom(OffsetTime.parse("10:00:00+02:00"));
        newOffice.setOpenUntil(OffsetTime.parse("17:30:00+02:00"));

        ResponseEntity<JsonOffice> response = newTemplate().postForEntity(resourceURL("/office/save.json"),
                JsonOffice.wrap(newOffice, clock), JsonOffice.class);

        assertEquals(CREATED, response.getStatusCode());

        Office savedOffice = response.getBody().unwrap();
        officesToCleanup.add(savedOffice);

        assertNotNull(savedOffice.getId());

        assertTrue(response.getHeaders().containsKey("Location"));

    }

    @Test @Ignore
    public void testGetAllOffices() {

        Office newOffice = new Office();
        newOffice.setCountry("NL");
        newOffice.setCity("Den Haag");
        newOffice.setOpenFrom(OffsetTime.parse("10:00:00+02:00"));
        newOffice.setOpenUntil(OffsetTime.parse("17:30:00+02:00"));

        ResponseEntity<List> response = newTemplate().getForEntity(resourceURL("/office/all.json"), List.class);

        List<JsonOffice> offices = response.getBody();
        List<String> countries = offices.stream().
                map(office -> office.getCountry()).sorted().collect(Collectors.toList());
        assertEquals(asList("AU", "BR", "DE"), countries);

    }

    private TestRestTemplate newTemplate() {
        TestRestTemplate template = new TestRestTemplate();
        template.setMessageConverters(jsonConverters);
        return template;
    }

    private String resourceURL(String resource) {
        return "http://localhost:" + serverPort + "/api" + resource;
    }

}