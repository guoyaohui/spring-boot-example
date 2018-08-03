package com.github.guoyaohui.booted;

import com.github.guoyaohui.business.SyncCaheDataWhenStarted;
import com.github.guoyaohui.business.LoaderTask;
import com.github.guoyaohui.domain.ServerNodeInfo;
import com.github.guoyaohui.schedule.KeepliveTask;
import com.github.guoyaohui.service.DistributeLockService;
import com.github.guoyaohui.service.SyncDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 郭垚辉
 * @date 2018/08/03
 */
@Slf4j
@Component
public class BootedInitCache {

    @Autowired
    private ServerNodeInfo currentServerNodeInfo;
    @Autowired
    private KeepliveTask keepliveTask;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private DistributeLockService distributeLockService;
    @Autowired
    private SyncDataService syncDataService;
    @Autowired
    private LoaderTask loaderTask;
    @Autowired
    private SyncCaheDataWhenStarted syncCaheDataWhenStarted;

    @EventListener
    public void executeSync(ContextRefreshedEvent event) throws InterruptedException {
        keepliveTask.keepliveSchedule();

        new Thread(loaderTask).start();
        new Thread(syncCaheDataWhenStarted).start();
    }
}
