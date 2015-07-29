package my.playground.office.repositories;

import my.playground.office.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Integer> {

    @Query( "SELECT o FROM Office o WHERE" +
            "   (o.openFrom<o.openUntil AND ?1 BETWEEN o.openFrom AND o.openUntil) OR" +
            "   (o.openFrom>o.openUntil AND ?1 NOT BETWEEN o.openUntil AND o.openFrom)")
    List<Office> findOfficesOpenAt(OffsetTime time);

}
