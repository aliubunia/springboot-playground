package my.playground.office;

import com.fasterxml.jackson.databind.Module;
import my.playground.office.endpoints.OfficeEndpoint;
import my.playground.office.support.Java8TimeModule;
import my.playground.office.support.JsonFeature;
import my.playground.office.support.OffsetTimeDeserializer;
import my.playground.office.support.OffsetTimeSerializer;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.ws.rs.ApplicationPath;
import java.time.OffsetTime;
import java.util.HashMap;
import java.util.Map;

import static org.glassfish.jersey.internal.InternalProperties.JSON_FEATURE;

@SpringBootApplication
public class OfficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficeApplication.class, args);
    }

    @Bean
    public ResourceConfig resourceConfig() {
        return new RESTApiConfig();
    }

    @Bean
    public Module java8TimeModule() {
        return new Java8TimeModule();
    }

    @ApplicationPath("/api")
    private static class RESTApiConfig extends ResourceConfig {
        public RESTApiConfig() {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(JSON_FEATURE, JsonFeature.class.getSimpleName());
            addProperties(configProps);
            register(JsonFeature.class);
            register(OfficeEndpoint.class);
        }
    }

}
