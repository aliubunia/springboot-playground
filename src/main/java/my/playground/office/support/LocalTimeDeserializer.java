package my.playground.office.support;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalTime;

import static java.time.format.DateTimeFormatter.ISO_TIME;
import static org.springframework.util.StringUtils.isEmpty;

public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

    @Override
    public LocalTime deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        String val = jp.getValueAsString();
        if (isEmpty(val)) {
            return null;
        }
        return LocalTime.parse(val, ISO_TIME);
    }

    @Override
    public Class<?> handledType() {
        return LocalTime.class;
    }
}
