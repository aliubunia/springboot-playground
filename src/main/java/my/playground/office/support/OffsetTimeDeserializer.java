package my.playground.office.support;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.OffsetTime;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_TIME;
import static org.springframework.util.StringUtils.isEmpty;

public class OffsetTimeDeserializer extends JsonDeserializer<OffsetTime> {

    @Override
    public OffsetTime deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        String val = jp.getValueAsString();
        if (isEmpty(val)) {
            return null;
        }
        return OffsetTime.parse(val, ISO_OFFSET_TIME);
    }

    @Override
    public Class<?> handledType() {
        return OffsetTime.class;
    }
}
