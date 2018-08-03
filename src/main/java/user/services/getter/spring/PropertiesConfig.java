package user.services.getter.spring;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropertiesConfig{

    @Bean
    public static PropertyPlaceholderConfigurer propertyConfigIn() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();

        configurer.setLocations(
                new ClassPathResource("properties/db.properties"),
                new ClassPathResource("properties/nfdump.properties"),
                new ClassPathResource("properties/threadPool.properties"),
                new ClassPathResource("properties/fileDateTimeMapper.properties")
        );

        configurer.setSystemPropertiesModeName("SYSTEM_PROPERTIES_MODE_OVERRIDE");
        configurer.setIgnoreResourceNotFound(false);
        return configurer;
    }
}

