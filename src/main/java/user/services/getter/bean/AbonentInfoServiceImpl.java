package user.services.getter.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.services.getter.dao.AbonentInfoDao;
import user.services.getter.model.Abonent;
import user.services.getter.services.AbonentInfoService;

import java.util.Collection;

@Service
public class AbonentInfoServiceImpl implements AbonentInfoService {

    @Autowired
    AbonentInfoDao abonentInfoDao;

    @Override
    public Abonent getAbonentById(Integer id) {
        return abonentInfoDao.getAbonentById(id);
    }

    @Override
    public Collection<Abonent> getAllAbonent() {
        return abonentInfoDao.getAllAbonents();
    }
}
