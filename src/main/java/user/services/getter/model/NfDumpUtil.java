package user.services.getter.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import user.services.getter.services.LogRawService;
import user.services.getter.services.RequestExecutionInfoService;
import user.services.getter.services.RequestService;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @Autowired
    LogRawService logRawService;

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

    private String getNfDumpPortFilter(Collection<String> ports) {
        if (ports != null && ports.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (String s : ports) {
                s=s.trim();
                if (s.matches("[0-9]*")) {
                    sb.append(" ");
                    sb.append(s);
                } else {
                    log.warn("Value {} is not port", s);
                }
            }
            if (sb.length() > 0) {
                return "port in [".concat(sb.toString().concat(" ]"));
            }
        }
        return null;
    }

    private String getNfDumpIpFilter(Collection<String> ipAddresses) {
        if (ipAddresses != null && ipAddresses.size() > 0) {
            StringBuilder sb = new StringBuilder();

            for (String s : ipAddresses) {
                s = s.trim();
                if (s.length() > 0) {
                    sb.append(" ");
                    sb.append(s);
                }
                if (sb.length() > 0) {
                    return "host in [".concat(sb.toString().concat(" ]"));
                }
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

        String tmpDir = "/tmp";
        StringBuilder sb = new StringBuilder();
        Integer exitCode = 0;
        request.setStatus(RequestStatus.PARSING);
        requestService.save(request);
        Collection<String> ipAddresses = request.getRequestedIpAddress();
        Collection<String> ports = request.getRequestedPorts();
        StringBuilder filter = new StringBuilder();
        Collection<LogRaw> logs = new HashSet<>();
        if (ipAddresses != null && ipAddresses.size() > 0) {
            String ipFilter = getNfDumpIpFilter(ipAddresses);
            if (ipFilter != null && ipFilter.length() > 0) {
                filter.append(ipFilter);
            }
        }

        if (ports != null && ports.size() > 0) {
            String portFilter = getNfDumpPortFilter(ports);
            if (portFilter != null && portFilter.length() > 0) {
                if (filter.length() > 0) {
                    filter.append("\\n AND \\n");
                }
                filter.append(portFilter);
            }
        }

        for (String file : files) {
            Runtime rt = Runtime.getRuntime();
            String[] commands = {nfDumpParserPath
                    ,dataDir
                    ,file
                    ,filter.toString()
                    ,request.getId().toString()
                    ,tmpDir};

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

            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(tmpDir + "/" + file + ".out"));
                String line = br.readLine();
                LogRaw logRaw = new LogRaw();

                while (line != null) {
                    if (line.contains("Flow Record:")) {
                        if (logRaw.getDstPort() != null) {
                            logs.add(logRaw);
                        }
                        logRaw = new LogRaw();
                    }

                    if (line.contains("src addr")) {
                        String[] element = line.split(" ");
                        logRaw.setSrcIp(ipToLong(element[element.length - 1]));
                    }

                    if (line.contains("dst addr")) {
                        String[] element = line.split(" ");
                        logRaw.setDstIp(ipToLong(element[element.length - 1]));
                    }

                    if (line.contains("dst addr")) {
                        String[] element = line.split(" ");
                        logRaw.setDstIp(ipToLong(element[element.length - 1]));
                    }

                    if (line.contains("dst port")) {
                        String[] element = line.split(" ");
                        logRaw.setDstPort(Integer.valueOf(element[element.length - 1]));
                    }

                    if (line.contains("src port")) {
                        String[] element = line.split(" ");
                        logRaw.setSrcPort(Integer.valueOf(element[element.length - 1]));
                    }

                    if (line.contains("(in)bytes")) {
                        String[] element = line.split(" ");
                        logRaw.setBytes(Integer.valueOf(element[element.length - 1]));
                    }

                    //3108375808 <  > 3108376063
                    if (line.contains("dst xlt ip")) {
                        if ((logRaw.getDstIp() <= 3108376063L)
                                && (logRaw.getDstIp() >= 3108375808L)) {
                            String[] element = line.split(" ");
                            logRaw.setNatIp(ipToLong(element[element.length - 1]));
                        }
                    }

                    //172.0.0.0 <> 172.255.255.255
                    //2885681152 <> 2902458367
                    if (line.contains("src xlt ip")) {
                        if ((logRaw.getSrcIp() >= 2885681152L)
                                && (logRaw.getSrcIp() <= 2902458367L)) {
                            String[] element = line.split(" ");
                            logRaw.setNatIp(ipToLong(element[element.length - 1]));
                        }
                    }

                    if (line.contains(" first")) {
                        String[] element = line.split(" ");
                        Long l = Long.valueOf(element[element.length - 3]);
                        logRaw.setDateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(l), ZoneId.of("UTC")));
                    }

                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
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

        log.info("Receiver {} rows", logs.size());
        logRawService.save(request.getId(), logs);

    }

    public String getNfDumpParserPath() {
        return nfDumpParserPath;
    }

    public void setNfDumpParserPath(String nfDumpParserPath) {
        this.nfDumpParserPath = nfDumpParserPath;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}

