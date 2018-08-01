package user.services.getter.dao;

import java.util.Collection;
import user.services.getter.model.Role;

public interface RoleDao {
    public Collection<Role> getAllRoles();
    public String getRoleNameById(Integer id);
    public Integer getRoleIdByName(String name);

}
