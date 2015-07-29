package my.playground.office.services;

import my.playground.office.model.Office;
import my.playground.office.repositories.OfficeRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.OffsetTime;
import java.util.List;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;

@Service
public class OfficeServiceImpl implements OfficeService {

    @Inject
    private OfficeRepository repo;

    @Inject
    private Clock clock;

    @Override
    @Transactional
    public Integer saveOffice(Office office) {
        Office entity = repo.saveAndFlush(office);
        return entity.getId();
    }

    @Override
    public Optional<Office> findOfficeByID(Integer id) {
        Office office = repo.findOne(id);
        return Optional.ofNullable(office);
    }

    @Override
    public List<Office> getAllOffices() {
        return repo.findAll();
    }

    @Override
    public List<Office> getOfficesOpenNow() {
        OffsetTime now = OffsetTime.ofInstant(clock.getTime(), UTC);
        return repo.findOfficesOpenAt(now);
    }

}
