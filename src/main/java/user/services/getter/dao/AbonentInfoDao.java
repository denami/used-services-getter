package user.services.getter.dao;

import user.services.getter.model.Abonent;

import java.util.Collection;

public interface AbonentInfoDao {
    Abonent getAbonentById(Integer id);
    Collection<Abonent> getAllAbonents();
}
