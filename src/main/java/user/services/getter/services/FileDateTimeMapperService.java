package user.services.getter.services;

import java.time.LocalDate;
import java.util.Collection;

public interface FileDateTimeMapperService {
    public Collection<String> getAllFiles();
    public Collection<String> getFilesForDateRange(LocalDate startDate, LocalDate endDate);
    public void save(Collection<String> files);
}
