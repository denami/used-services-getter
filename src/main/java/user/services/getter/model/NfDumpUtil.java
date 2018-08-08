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
import java.util.HashSet;

@Component
@Scope("prototype")
public class NfDumpUtil implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(NfDumpUtil.class);

    @Value("${nfdump.base.dir}")
    String dataDir;

    @Value("${nfdump.parser.path}")
    String nfDumpParserPath;

    @Autowired
    RequestService requestService;

    @Autowired
    RequestExecutionInfoService requestExecutionInfoService;

    Collection<String> files;
    Integer id;
    private Request request;

    public Collection<String> getFiles() {
        return files;
    }

    public void setFiles(Collection<String> files) {
        this.files = files;
    }

    public String getDataDir() {
        return dataDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }

    private String getIpGrepString(Collection<String> ipAddresses) {
        StringBuilder sb = new StringBuilder();
        Collection<String> grepIps = new HashSet<String>();

        if (ipAddresses != null){
            for (String s : ipAddresses){
                Long longIp = ipToLong(s);
                if (longIp != 0) {
                    StringBuilder regexpIpElement = new StringBuilder("\\|");
                    regexpIpElement.append(longIp);
                    regexpIpElement.append("\\|");
                    grepIps.add(regexpIpElement.toString());
                }
            }

            if ( grepIps.size()>0){
                sb.append("(");
                sb.append(String.join("|", grepIps));
                sb.append(")");
                return sb.toString();
            }
        }
        return null;
    }

    private Long ipToLong(String ipAddress) {
        String[] ipAddressInArray = ipAddress.split("\\.");
        Long result = 0L;

        if (ipAddressInArray.length == 4) {
            for (Integer i = 0; i < ipAddressInArray.length; i++) {
                Integer power = 3 - i;
                Integer ip = Integer.parseInt(ipAddressInArray[i]);
                Double partResult = (ip * Math.pow(256, power));
                result += partResult.longValue();
            }
        }
        return result;
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();
        Integer exitCode = 0;
        request.setStatus(RequestStatus.PARSING);
        requestService.save(request);
        Collection<String> ipAddresses = request.getRequestedIpAddress();
        String grepIntIpAddresses = "\\|";
        if (ipAddresses != null){
            grepIntIpAddresses = getIpGrepString(ipAddresses);
        }

        for (String file : files) {
            Runtime rt = Runtime.getRuntime();
            String[] commands = {nfDumpParserPath,
                    dataDir,
                    file,
                    grepIntIpAddresses,
                    request.getId().toString()};

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
                    log.warn("NfDumpUtil complete unsuccessful:{}", request.getId());
                    RequestExecutionInfo info = requestExecutionInfoService.getInfoByRequestId(request.getId());
                    sb.append("\\n");
                    if(info.getMessage() == null) {
                        info.setMessage(sb.toString());
                    } else {
                        info.setMessage(info.getMessage().concat(sb.toString()));
                    }
                    requestExecutionInfoService.save(info);
                }
            }
        }
        if (exitCode == 0) {
            log.info("NfDumpUtil complete successful:{}", id);
            request.setStatus(RequestStatus.PARSED);
            requestService.save(request);

        } else {
            log.error("NfDumpUtil complete unsuccessful:{}", id);
            request.setStatus(RequestStatus.FAIL);
            requestService.save(request);
        }

    }

    public String getNfDumpParserPath() {
        return nfDumpParserPath;
    }

    public void setNfDumpParserPath(String nfDumpParserPath) {
        this.nfDumpParserPath = nfDumpParserPath;
    }

    public void setRequest(Request request) {
        this.request=request;
    }
}