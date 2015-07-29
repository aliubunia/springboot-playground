package my.playground.office.services;

import my.playground.office.model.Office;

import java.util.List;
import java.util.Optional;

public interface OfficeService {

    Optional<Office> findOfficeByID(Integer id);

    Integer saveOffice(Office office);

    List<Office> getAllOffices();

    List<Office> getOfficesOpenNow();

}
