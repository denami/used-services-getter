package user.services.getter.spring;

import org.springframework.security.crypto.password.PasswordEncoder;

public class NoCryptPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.matches(rawPassword.toString());
    }
}
