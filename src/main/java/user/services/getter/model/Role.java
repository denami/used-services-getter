package user.services.getter.model;

import org.springframework.security.access.method.P;

public class Role implements Comparable<Role> {
    private Integer id;
    private String rolename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", rolename='" + rolename + '\'' +
                '}';
    }

    @Override
    public int compareTo(Role o) {

        if (o.getId() > id) {
            return 1;
        }

        if (o.getId() < id) {
            return -1;
        }

        return 0;
    }
}
