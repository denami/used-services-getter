package user.services.getter.dao;

import java.time.LocalDate;
import java.util.Collection;

public interface FileDateTimeMapperDao {
    public Collection<String> getAllFiles();
    public Collection<String> getFilesForDateRange(LocalDate startDate, LocalDate endDate);
    public void save(Collection<String> files);
}
