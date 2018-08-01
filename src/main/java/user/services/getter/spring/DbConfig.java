package user.services.getter.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
//@PropertySource("classpath:resources/properties/db.properties")
public class DbConfig {

    @Value("${driver.setDriverClassName}")
    String driverClassName;

    @Value("${driver.setUrl}")
    String urlSet;

    @Value("${driver.username}")
    String username;

    @Value("${driver.password}")
    String password;

    @Bean(name = "dataSource")
    public DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName(driverClassName);
        driver.setUrl(urlSet);
        driver.setUsername(username);
        driver.setPassword(password);
        return driver;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(driverManagerDataSource());
    }
}
