package my.playground.office.services;

import my.playground.office.model.Office;
import my.playground.office.repositories.OfficeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class OfficeServiceImplTest {

    static final int ID_OFFICE_1 = 123;
    static final int ID_OFFICE_2 = 133;

    @InjectMocks
    OfficeServiceImpl target;

    @Mock
    OfficeRepository repo;

    @Mock
    Clock clock;

    Office office1;
    Office office2;

    Instant instant;

    @Before
    public void setUp() {
        office1 = createOffice(ID_OFFICE_1, "NL", "Amsterdam");
        office2 = createOffice(ID_OFFICE_2, "DE", "Berlin");
        instant = OffsetDateTime.parse("2015-07-25T11:00:00+02:00").toInstant();
    }

    @Test
    public void testSave() {
        when(repo.saveAndFlush(office1)).thenReturn(office1);

        Integer id = target.saveOffice(office1);

        assertEquals((Integer) ID_OFFICE_1, id);
    }

    @Test
    public void testFindOfficeByID() {
        when(repo.findOne(ID_OFFICE_1)).thenReturn(office1);

        Optional<Office> result = target.findOfficeByID(ID_OFFICE_1);

        assertTrue(result.isPresent());
        assertEquals(office1, result.get());
    }

    @Test
    public void testFindOfficeByID_NotFound() {
        when(repo.findOne(ID_OFFICE_1)).thenReturn(null);

        Optional<Office> result = target.findOfficeByID(ID_OFFICE_1);

        assertFalse(result.isPresent());
    }

    @Test
    public void testGetAllOffices() {
        List<Office> officeList = asList(office1, office2);

        when(repo.findAll()).thenReturn(officeList);

        List<Office> result = target.getAllOffices();

        assertEquals(officeList, result);
    }

    @Test
    public void testGetOfficesOpenNow() {
        List<Office> officeList = asList(office1, office2);

        when(clock.getTime()).thenReturn(instant);
        ArgumentCaptor<OffsetTime> captor = ArgumentCaptor.forClass(OffsetTime.class);
        when(repo.findOfficesOpenAt(captor.capture())).thenReturn(officeList);

        List<Office> result = target.getOfficesOpenNow();

        assertEquals(officeList, result);
        assertEquals(LocalTime.parse("09:00:00"), captor.getValue().toLocalTime());
    }

    private Office createOffice(Integer id, String country, String city) {
        Office office = new Office();
        office.setId(id);
        office.setCountry(country);
        office.setCity(city);
        return office;
    }

}
