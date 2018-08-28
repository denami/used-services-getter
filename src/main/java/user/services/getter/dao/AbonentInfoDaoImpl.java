package user.services.getter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import user.services.getter.JDBCTemplate.AbonentJDBCTemplate;
import user.services.getter.model.Abonent;

import java.util.Collection;

@Repository
public class AbonentInfoDaoImpl implements AbonentInfoDao {

    @Autowired
    AbonentJDBCTemplate abonentJDBCTemplate;

    @Override
    public Abonent getAbonentById(Integer id) {
        return abonentJDBCTemplate.getAbonentById(id);
    }

    @Override
    public Collection<Abonent> getAllAbonents() {
        return abonentJDBCTemplate.getAllAbonents();
    }
}
