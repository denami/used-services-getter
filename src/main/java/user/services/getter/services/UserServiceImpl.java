package user.services.getter.services;

import org.springframework.beans.factory.annotation.Autowired;
import user.services.getter.dao.UserDao;
import user.services.getter.model.Role;
import user.services.getter.model.User;

import java.util.Collection;

public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User getUserById(Integer id) {
        return null;
    }

    @Override
    public User getUserByLogin(String login) {
        return userDao.getUserByName(login);
    }

    @Override
    public Collection<Role> getUserRoles(User user) {
        return null;
    }

    @Override
    public Role getRoleByName(String name) {
        return null;
    }

    @Override
    public Role getRoleById(Integer id) {
        return null;
    }

    @Override
    public Collection<Role> getAllRoles() {
        return null;
    }

    @Override
    public Collection<User> getAllUsers() {
        return null;
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public void setUserRole(User user, Role role) {

    }

}
