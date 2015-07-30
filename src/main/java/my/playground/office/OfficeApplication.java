package my.playground.office;

import com.fasterxml.jackson.databind.Module;
import my.playground.office.endpoints.OfficeEndpoint;
import my.playground.office.support.Java8TimeModule;
import my.playground.office.support.JsonFeature;
import org.apache.catalina.Context;
import org.apache.catalina.realm.MemoryRealm;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.ws.rs.ApplicationPath;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.glassfish.jersey.internal.InternalProperties.JSON_FEATURE;

@SpringBootApplication
public class OfficeApplication extends SpringBootServletInitializer {

    @Bean
    public ResourceConfig resourceConfig() {
        return new RESTApiConfig();
    }

    @Bean
    public Module java8TimeModule() {
        return new Java8TimeModule();
    }

    @Bean
    public TomcatEmbeddedServletContainerFactory containerFactory() {
        TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();
        tomcatFactory.addContextCustomizers(new TomcatContextCustomizer() {
            @Override
            public void customize(Context context) {
                MemoryRealm realm = new MemoryRealm();
                try (InputStream stream = getClass().getResourceAsStream("/tomcat-users.xml")) {
                    Path file = Files.createTempFile("tomcat-users", "xml");
                    Files.delete(file);
                    Files.copy(stream, file);
                    realm.setPathname(file.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                context.setRealm(realm);
            }
        }
        );
        return tomcatFactory;
    }

    @ApplicationPath("/api")
    private static class RESTApiConfig extends ResourceConfig {
        public RESTApiConfig() {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(JSON_FEATURE, JsonFeature.class.getSimpleName());
            addProperties(configProps);
            register(JsonFeature.class);
            register(RolesAllowedDynamicFeature.class);
            register(OfficeEndpoint.class);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(OfficeApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(OfficeApplication.class);
    }

}
