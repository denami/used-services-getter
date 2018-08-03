package user.services.getter.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import user.services.getter.services.FileDateTimeMapperService;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

@Component
@Scope("prototype")
public class FileListUpdater implements Runnable {

    @Value("${nfdump.base.dir}")
    String dataDir;

    @Autowired
    FileDateTimeMapperService fileDateTimeMapperService;

    public String getDataDir() {
        return dataDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }

    @Override
    public void run() {
        File folder = new File(dataDir);
        Collection<String> files = new HashSet<String>();
        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isFile() && file.getName().matches("^nfcapd\\.[0-9]{12}")) {
                    files.add(file.getName());
                }
            }
        }
        if (files.size()>0){
            fileDateTimeMapperService.save(files);
        }
    }


}
