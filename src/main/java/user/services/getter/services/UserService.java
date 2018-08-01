package user.services.getter.services;

import user.services.getter.model.Role;
import user.services.getter.model.User;

import java.util.Collection;

public interface UserService {
    public User getUserById(Integer id);
    public User getUserByLogin(String login);
    public Collection<Role> getUserRoles(User user);
    public Role getRoleByName(String name);
    public Role getRoleById(Integer id);
    public Collection<Role> getAllRoles();
    public Collection<User> getAllUsers();
    public User saveUser(User user);
    public void setUserRole(User user, Role role);
}
