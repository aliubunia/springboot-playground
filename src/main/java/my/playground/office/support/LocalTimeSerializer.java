package my.playground.office.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalTime;

import static java.time.format.DateTimeFormatter.ISO_TIME;

public class LocalTimeSerializer extends JsonSerializer<LocalTime> {

    @Override
    public void serialize(LocalTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (value != null) {
            jgen.writeString(ISO_TIME.format(value));
        }
    }

    @Override
    public Class<LocalTime> handledType() {
        return LocalTime.class;
    }
}
