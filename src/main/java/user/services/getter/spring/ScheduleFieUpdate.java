package user.services.getter.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import user.services.getter.model.FileListUpdater;

@Component
public class ScheduleFieUpdate {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTasks.class);

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    @Scheduled(fixedRate = 60000)
    public void updateFileList() {
        if (threadPoolTaskExecutor.getMaxPoolSize() - threadPoolTaskExecutor.getActiveCount() > 0) {
            logger.debug("Update file list");
            FileListUpdater fileListUpdater = applicationContext.getBean(FileListUpdater.class);
            threadPoolTaskExecutor.execute(fileListUpdater);
        }
    }
}
