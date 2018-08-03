package user.services.getter.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.services.getter.dao.FileDateTimeMapperDao;
import user.services.getter.services.FileDateTimeMapperService;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class FileDateTimeMapperServiceImpl implements FileDateTimeMapperService {

    @Autowired
    FileDateTimeMapperDao fileDateTimeMapperDao;

    @Override
    public Collection<String> getAllFiles() {

        return null;
    }

    @Override
    public Collection<String> getFilesForDateRange(LocalDate startDate, LocalDate endDate) {
        return fileDateTimeMapperDao.getFilesForDateRange(startDate,endDate);
    }

    @Override
    public void save(Collection<String> files) {
        fileDateTimeMapperDao.save(files);
    }
}
