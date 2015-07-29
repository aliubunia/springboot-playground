package my.playground.office.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.OffsetTime;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_TIME;

public class OffsetTimeSerializer extends JsonSerializer<OffsetTime> {

    @Override
    public void serialize(OffsetTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (value != null) {
            jgen.writeString(ISO_OFFSET_TIME.format(value));
        }
    }

    @Override
    public Class<OffsetTime> handledType() {
        return OffsetTime.class;
    }
}
