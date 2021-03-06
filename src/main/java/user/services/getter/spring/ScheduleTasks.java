package user.services.getter.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import user.services.getter.model.*;
import user.services.getter.services.FileDateTimeMapperService;
import user.services.getter.services.RequestExecutionInfoService;
import user.services.getter.services.RequestService;

import java.text.SimpleDateFormat;

@Component
public class ScheduleTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private RequestService requestService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private FileDateTimeMapperService fileDateTimeMapperService;

    @Autowired
    private RequestExecutionInfoService requestExecutionInfoService;

    @Scheduled(fixedRate = 5000)
    public void scheduleTasks() {

        if (threadPoolTaskExecutor.getMaxPoolSize() - threadPoolTaskExecutor.getActiveCount() > 0) {
            Request request = requestService.getRequestByStatus(RequestStatus.PLANNED);
            if (request != null){
                request.setStatus(RequestStatus.PREPARATION);
                requestService.save(request);
                logger.info("Prepare request: {}", request.getId());

                RequestExecutionInfo info = requestExecutionInfoService.getInfoByRequestId(request.getId());
                info.setNfFiles(
                        fileDateTimeMapperService.getFilesForDateRange(
                                request.getStartDate(),
                                request.getEndDate()));
                requestExecutionInfoService.save(info);
                request.setStatus(RequestStatus.PREPARED);
                requestService.save(request);
            }
        }

        if (threadPoolTaskExecutor.getMaxPoolSize() - threadPoolTaskExecutor.getActiveCount() > 0) {
            Request request = requestService.getRequestByStatus(RequestStatus.PREPARED);
            if (request != null) {
                String taskName = request.getId() + "-" + request.getStatus().name();
                NfDumpUtil nfDumpUtil =applicationContext.getBean(NfDumpUtil.class);
                logger.info("Add {} to thread", taskName);
                nfDumpUtil.setRequest(request);
                RequestExecutionInfo info = requestExecutionInfoService.getInfoByRequestId(request.getId());
                nfDumpUtil.setFiles(info.getNfFiles());
                threadPoolTaskExecutor.execute(nfDumpUtil);
            }
        }

        if (threadPoolTaskExecutor.getMaxPoolSize() - threadPoolTaskExecutor.getActiveCount() > 0) {
            Request request = requestService.getRequestByStatus(RequestStatus.PARSED);
            if (request != null) {
                String taskName = request.getId() + "-" + request.getStatus().name();
                UserIpTimeMapper userIpTimeMapper = applicationContext.getBean(UserIpTimeMapper.class);
                logger.info("Add {} to thread", taskName);
                userIpTimeMapper.setRequest(request);
                threadPoolTaskExecutor.execute(userIpTimeMapper);
            }
        }
    }
}
