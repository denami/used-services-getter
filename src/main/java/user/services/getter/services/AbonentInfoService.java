package user.services.getter.services;

import user.services.getter.model.Abonent;

import java.util.Collection;

public interface AbonentInfoService {

    public Abonent getAbonentById(Integer id);
    public Collection<Abonent> getAllAbonent();

}
