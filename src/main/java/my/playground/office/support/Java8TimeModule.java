package my.playground.office.support;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

import java.time.LocalTime;
import java.time.OffsetTime;

public class Java8TimeModule extends Module {

    @Override
    public void setupModule(SetupContext context) {
        SimpleSerializers serializers = new SimpleSerializers();
        serializers.addSerializer(new LocalTimeSerializer());
        serializers.addSerializer(new OffsetTimeSerializer());
        context.addSerializers(serializers);

        SimpleDeserializers deserializers = new SimpleDeserializers();
        deserializers.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        deserializers.addDeserializer(OffsetTime.class, new OffsetTimeDeserializer());
        context.addDeserializers(deserializers);
    }

    @Override
    public String getModuleName() {
        return "Java8TimeModule";
    }

    @Override
    public Version version() {
        return new Version(0, 1, 0, "SNAPSHOT", null, null);
    }

}
