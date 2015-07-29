package my.playground.office.support;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.base.JsonMappingExceptionMapper;
import com.fasterxml.jackson.jaxrs.base.JsonParseExceptionMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.internal.InternalProperties;

import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

public class JsonFeature implements Feature {

    private static final String FEATURE_IMPL = JsonFeature.class.getSimpleName();

    @Override
    public boolean configure(FeatureContext context) {
        Configuration config = context.getConfiguration();

        String jsonFeatureImpl = CommonProperties.getValue(config.getProperties(), config.getRuntimeType(),
                InternalProperties.JSON_FEATURE, FEATURE_IMPL, String.class);

        if (!FEATURE_IMPL.equalsIgnoreCase(jsonFeatureImpl)) {
            return false;
        }

        if (!config.isRegistered(JacksonJsonProvider.class)) {
            context.register(JsonParseExceptionMapper.class);
            context.register(JsonMappingExceptionMapper.class);
            context.register(createJsonProvider(), MessageBodyReader.class, MessageBodyWriter.class);
        }

        return true;
    }

    protected JacksonJsonProvider createJsonProvider() {
        return new JacksonJsonProvider(createObjectMapper());
    }

    protected ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Java8TimeModule());
        return mapper;
    }
}