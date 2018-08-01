package user.services.getter.dao;


import user.services.getter.model.User;

import java.util.Collection;

public interface UserDao {
    public Collection<User> getAllUsers();
    public User getUserByName(String name);
    public User getUserById(Integer id);
}
