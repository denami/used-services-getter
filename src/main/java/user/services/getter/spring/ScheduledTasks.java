package user.services.getter.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import user.services.getter.model.NfDumpParser;
import user.services.getter.model.Request;
import user.services.getter.model.RequestStatus;
import user.services.getter.services.RequestService;

import java.text.SimpleDateFormat;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    RequestService requestService;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    NfDumpParser nfDumpParser;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {

        if (threadPoolTaskExecutor.getMaxPoolSize()-threadPoolTaskExecutor.getActiveCount()>0) {
            Request request = requestService.getRequestByStatus(RequestStatus.SCHEDULED);
            if (request != null) {

                String taskName = request.getId()
                        + "-"
                        + request.getStatus().name();
                log.info("Add {} to thread", taskName);
                nfDumpParser.setId(request.getId());
                nfDumpParser.setDataBaseDir("${nfdump.base.dir}");
                nfDumpParser.setNfDumpPath("${nfdump.path}");
                threadPoolTaskExecutor.execute(nfDumpParser);
            }
        }
    }
}
