package user.services.getter.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import user.services.getter.bean.UserDetailsServiceImpl;
import user.services.getter.services.RequestService;
import user.services.getter.services.RequestServiceImpl;
import user.services.getter.services.UserService;
import user.services.getter.services.UserServiceImpl;

@EnableWebMvc
@Configuration
@ComponentScan({"user.services.getter.*"})
@Import(PropertiesConfig.class)
public class AppConfig {

    @Bean
    public ConversionService conversionService() {
        return new DefaultConversionService();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new NoCryptPasswordEncoder();
    }

    @Bean(name = "userService")
    public UserService getUserService() {
        return new UserServiceImpl();
    }

    @Bean(name = "requestService")
    public RequestService getRequestService() {
        return new RequestServiceImpl();
    }

    @Bean
    public UserDetailsService getUserDetailsService(){return new UserDetailsServiceImpl();}

}
