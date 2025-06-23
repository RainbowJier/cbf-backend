package com.cbf.framework.manager;

import com.cbf.common.utils.Threads;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Asynchronous task manager
 *
 * @author Frank
 */

@Slf4j
@Component
public class AsyncManager {
    /**
     * Operate delay time, default is 10 milliseconds.
     */
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * Thread pool, bean of ScheduledExecutorService
     */
    @Resource
    @Qualifier("scheduledExecutorService")
    private ScheduledExecutorService scheduledExecutor;

    /**
     * Thread pool, bean of ThreadPoolTaskExecutor
     */
    @Resource
    @Qualifier("threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * Execute task.
     * Execute task after 10 milliseconds.
     * @param task 任务
     */
    public void execute(TimerTask task) {
        scheduledExecutor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * Shutdown the scheduled executor service.
     */
    @PreDestroy
    private void shutdown() {
        try {
            log.info("====Shutdown the task thread pool.====");
            Threads.shutdownAndAwaitTermination(scheduledExecutor);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
