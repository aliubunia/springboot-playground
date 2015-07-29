package my.playground.office.support;

import org.springframework.util.Assert;

import java.time.*;

import static java.time.ZoneOffset.UTC;

public class Java8TimeUtils {

    public static LocalTime toUTC(OffsetTime offsetTime, my.playground.office.services.Clock clock) {
        Assert.notNull(clock);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(clock.getTime(), UTC);
        return OffsetDateTime.of(LocalDate.from(localDateTime), offsetTime.toLocalTime(), offsetTime.getOffset()).
                atZoneSameInstant(UTC).toLocalTime();
    }
}
