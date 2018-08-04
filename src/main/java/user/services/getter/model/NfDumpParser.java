package user.services.getter.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import user.services.getter.services.RequestExecutionInfoService;
import user.services.getter.services.RequestService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

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

    @Autowired
    RequestExecutionInfoService requestExecutionInfoService;

    Collection<String> files;
    Integer id;

    public String getNfDumpPath() {
        return nfDumpPath;
    }

    public void setNfDumpPath(String nfDumpPath) {
        this.nfDumpPath = nfDumpPath;
    }

    public Collection<String> getFiles() {
        return files;
    }

    public void setFiles(Collection<String> files) {
        this.files = files;
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
        StringBuilder sb = new StringBuilder();
        Integer exitCode = 0;
        Request request = requestService.getRequestById(id);
        request.setStatus(RequestStatus.PARSING);
        requestService.save(request);

        for (String file : files) {
            Runtime rt = Runtime.getRuntime();
            String[] commands = {nfDumpPath, "-r", dataDir + "/" + file + "-o pipe"};

            try {
                Process p = rt.exec(commands);
                p.waitFor();
                BufferedReader stdInput = new BufferedReader(new
                        InputStreamReader(p.getInputStream()));

                BufferedReader stdError = new BufferedReader(new
                        InputStreamReader(p.getErrorStream()));

                String s = null;
                if (p.exitValue() != 0) {
                    exitCode = p.exitValue();
                    while ((s = stdInput.readLine()) != null) {
                        sb.append(s);
                    }
                }
                if (p.exitValue() != 0) {
                    exitCode = p.exitValue();
                    while ((s = stdError.readLine()) != null) {
                        sb.append(s);
                    }
                }
            } catch (IOException e) {
                log.error(e.getLocalizedMessage());
            } catch (InterruptedException e) {
                log.warn(e.getLocalizedMessage());
            } finally {
                if (exitCode != 0) {
                    request.setStatus(RequestStatus.FAIL);
                    log.info("NfDumpParser complete successful:{}", id);
                    RequestExecutionInfo info = requestExecutionInfoService.getInfoByRequestId(request.getId());
                    info.setMessage(sb.toString());
                    requestExecutionInfoService.save(info);
                }
            }
        }
        if (exitCode == 0) {
            request.setStatus(RequestStatus.PARSED);
            requestService.save(request);
        }

    }
}

