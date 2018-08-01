package user.services.getter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import user.services.getter.JDBCTemplate.UserJDBCTemplate;
import user.services.getter.model.User;

import java.util.Collection;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    UserJDBCTemplate userJDBCTemplate;

    @Override
    public Collection<User> getAllUsers() {
        return userJDBCTemplate.getAllUsers();
    }

    @Override
    public User getUserByName(String name) {
        return userJDBCTemplate.getUserByLogin(name);
    }

    @Override
    public User getUserById(Integer id) {
        return userJDBCTemplate.getUserById(id);
    }
}
