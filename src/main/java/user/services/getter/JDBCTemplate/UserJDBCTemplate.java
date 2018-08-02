package user.services.getter.JDBCTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import user.services.getter.model.Role;
import user.services.getter.model.User;
import user.services.getter.services.UserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


@Component
public class UserJDBCTemplate implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserJDBCTemplate.class);

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserById(Integer id) {
        return null;
    }

    @Override
    public User getUserByLogin(String login) {
        logger.info("Get user by login: {}", login);

        String SQL = "SELECT id, login, password, description FROM getter_user WHERE login = ?";
        try {
            User user = jdbcTemplate.queryForObject(SQL, new Object[]{login}, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user1 = new User();
                    user1.setId(rs.getInt("id"));
                    user1.setLogin(rs.getString("login"));
                    user1.setPassword(rs.getString("password"));
                    user1.setDescription(rs.getString("description"));
                    return user1;
                }
            });
            return user;

        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            logger.warn(emptyResultDataAccessException.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public Collection<Role> getUserRoles(User user) {
        String SQL = "SELECT name FROM getter_role WHERE user_id = ?";
        List<Role> roles = jdbcTemplate.query(SQL, new Object[]{user.getId()}, new RowMapper<Role>() {
            @Override
            public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
                Role role = new Role();
                role.setRolename(rs.getString("name"));
                return role;
            }
        });

        Collection<Role> rolesCollection = new HashSet<>();
        rolesCollection.addAll(roles);

        return rolesCollection;
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

        if (getUserByLogin(user.getLogin()) != null) {
            logger.info("Add new user: {}", user);
            String SQL = "UPDATE getter_user SET password=?, description = ? WHERE login = ?";
            jdbcTemplate.update(SQL,
                    user.getPassword(),
                    user.getDescription());
        } else {
            logger.info("Updating user: {}", user);
            String SQL="INSERT INTO getter_user(login,password,description) VALUES (?,?,?)";
            jdbcTemplate.update(SQL,
                    user.getLogin(),
                    user.getPassword(),
                    user.getPassword());
        }
        return getUserByLogin(user.getLogin());
    }

    @Override
    public void setUserRole(User user, Role role) {

    }
}
