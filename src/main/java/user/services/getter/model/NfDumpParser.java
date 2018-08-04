package user.services.getter.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import user.services.getter.services.RequestService;

import java.io.IOException;

@Component
@Scope("prototype")
public class NfDumpParser implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(NfDumpParser.class);

    @Value("${nfdump.base.dir}")
    String dataDir;

    @Value("${nfdump.path}")
    String nfDumpPath;

    @Autowired
    RequestService requestService;

    String fileName;
    Integer id;

    public String getNfDumpPath() {
        return nfDumpPath;
    }

    public void setNfDumpPath(String nfDumpPath) {
        this.nfDumpPath = nfDumpPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataDir() {
        return dataDir;
    }

    public void setDataBaseDir(String databaseDir) {
        this.dataDir = databaseDir;
    }

    @Override
    public void run() {
        Runtime rt = java.lang.Runtime.getRuntime();
        String[] commands = {nfDumpPath, "-r", dataDir + "/" + fileName + "-o pipe" };
        Integer exitCode = -1;

        Request request = requestService.getRequestById(id);
        request.setStatus(RequestStatus.PARSING);
        requestService.save(request);

        try {
            Process p = rt.exec(commands);
            p.waitFor();
            exitCode=p.exitValue();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        } catch (InterruptedException e) {
            log.warn(e.getLocalizedMessage());
        } finally {
            if(exitCode!=0){
                request.setStatus(RequestStatus.FAIL);
                log.info("NfDumpParser complete successful:{}", id);
            } else {
                request.setStatus(RequestStatus.PARSED);
            }
            requestService.save(request);
        }

        if (exitCode==0) {

        }
    }
}