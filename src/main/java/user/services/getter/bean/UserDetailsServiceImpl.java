package user.services.getter.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import user.services.getter.model.Role;
import user.services.getter.services.UserService;

import java.util.*;

@Service("authService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        user.services.getter.model.User user = userService.getUserByLogin(username);
        List<GrantedAuthority> authorities = buildUserAuthority(userService.getUserRoles(user));

        return null;
    }

    private UserDetails buildUserForAuthentication(user.services.getter.model.User user,
                                                   List<GrantedAuthority> authorities) {
        return new User(user.getLogin(),user.getPassword(), authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Collection<Role> userRoles) {
        List<GrantedAuthority> result = new ArrayList<>();

        for (Role role : userRoles.toArray(new Role[userRoles.size()] )) {
            result.add(new SimpleGrantedAuthority(role.toString()));
        }

        return result;
    }



}
