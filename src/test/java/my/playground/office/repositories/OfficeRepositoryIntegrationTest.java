package my.playground.office.repositories;

import jersey.repackaged.com.google.common.collect.Sets;
import my.playground.office.OfficeApplication;
import my.playground.office.model.Office;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.ZoneOffset.UTC;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OfficeApplication.class)
public class OfficeRepositoryIntegrationTest {

    @Inject
    private OfficeRepository repo;

    @Test
    public void testGetAllOffices() {
        List<Office> offices = repo.findAll();
        assertOffices(offices, "DE", "BR", "AU");
    }

    @Test
    public void testGetOfficesOpenAt_1AM() {
        LocalTime localTime = LocalTime.of(1, 0);
        OffsetTime time = OffsetTime.of(localTime, UTC);
        List<Office> offices = repo.findOfficesOpenAt(time);
        assertOffices(offices, "AU");
    }

    @Test
    public void testGetOfficesOpenAt_2PM() {
        OffsetTime time = OffsetTime.of(LocalTime.of(14, 0), UTC);
        List<Office> offices = repo.findOfficesOpenAt(time);
        assertOffices(offices, "DE", "BR");
    }

    @Test
    public void testGetOfficesOpenAt_8PM() {
        OffsetTime time = OffsetTime.of(LocalTime.of(20, 0), UTC);
        List<Office> offices = repo.findOfficesOpenAt(time);
        assertOffices(offices, "BR");
    }

    public void assertOffices(List<Office> offices, String... expectedCountries) {
        Set<String> countries = offices.stream().
                map(office -> office.getCountry()).
                collect(Collectors.toSet());
        assertEquals(Sets.newHashSet(expectedCountries), countries);
    }

}
