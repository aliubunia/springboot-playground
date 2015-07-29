package my.playground.office.endpoints;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import my.playground.office.model.Office;
import my.playground.office.services.Clock;

import java.time.LocalTime;

import static my.playground.office.support.Java8TimeUtils.toUTC;
import static org.springframework.beans.BeanUtils.copyProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonOffice extends Office {

    protected Clock clock;

    public LocalTime getOpenFromUTC() {
        return toUTC(openFrom, clock);
    }

    public LocalTime getOpenUntilUTC() {
        return toUTC(openUntil, clock);
    }

    public Office unwrap() {
        Office office = new Office();
        copyProperties(this, office);
        return office;
    }

    public static JsonOffice wrap(Office office, Clock clock) {
        JsonOffice jsonOffice = new JsonOffice();
        copyProperties(office, jsonOffice);
        jsonOffice.clock = clock;
        return jsonOffice;
    }

}
