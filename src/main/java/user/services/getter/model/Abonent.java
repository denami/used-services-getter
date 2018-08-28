package user.services.getter.model;

import java.util.Objects;

public class Abonent implements Comparable<Abonent> {
    private Integer id;
    private String name;
    private String address;

    public Abonent() {}

    public Abonent(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Abonent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public int compareTo(Abonent o) {
        if (id.equals(o.getId())) {
            if (name.equals(o.getName())) {
                if (address.equals(o.getAddress())) {
                    return 0;
                } else return address.compareTo(o.getAddress());
            } else return name.compareTo(o.getAddress());
        } else return id.compareTo(o.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Abonent abonent = (Abonent) o;
        return id == abonent.id &&
                Objects.equals(name, abonent.name) &&
                Objects.equals(address, abonent.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }
}
