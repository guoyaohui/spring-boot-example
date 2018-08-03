package com.github.guoyaohui.business;

import com.github.guoyaohui.domain.ServerNodeInfo;
import com.github.guoyaohui.domain.enums.CacheDataSyncStatus;
import com.github.guoyaohui.domain.enums.ServerRoleType;
import com.github.guoyaohui.schedule.KeepliveTask;
import com.github.guoyaohui.service.DistributeLockService;
import com.github.guoyaohui.service.SyncDataService;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 只有在系统启动的时候才会进行一次执行操作
 *
 * @author 郭垚辉
 * @date 2018/08/03
 */
@Slf4j
@Component
public class SyncCaheDataWhenStarted implements Runnable {

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

    @Override
    public void run() {
        if (ServerRoleType.WRITEER_AND_READER.equals(currentServerNodeInfo.getRoleType())) {
            syncDataService.setSyncStatusIfAbsent(CacheDataSyncStatus.SYNCING);
            log.info("【{}】时，【{}】 节点开始进行数据同步，请稍后", new Date(), currentServerNodeInfo.getName());
            try {
                Thread.sleep(1000 * 60 * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            syncDataService.setSyncStatusIfAbsent(CacheDataSyncStatus.SYNC_FINISH);
            log.info("【{}】 数据同步已经结束，可以提供正常服务", new Date());
        } else {
            log.info("【{}】 节点不是【{}】节点，因此不需要进行数据同步的操作...", currentServerNodeInfo.getName(), currentServerNodeInfo.getRoleType().getLabel());
        }
    }
}
