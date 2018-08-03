package com.github.guoyaohui.schedule;

import com.github.guoyaohui.constants.RedisConstants;
import com.github.guoyaohui.domain.ServerNodeInfo;
import com.github.guoyaohui.domain.dto.DistributeLockDTO;
import com.github.guoyaohui.domain.enums.CacheDataSyncStatus;
import com.github.guoyaohui.domain.enums.ServerRoleType;
import com.github.guoyaohui.service.DistributeLockService;
import com.github.guoyaohui.service.SyncDataService;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 郭垚辉
 * @date 2018/08/03
 */
@Slf4j
@Component
public class KeepliveTask {

    @Autowired
    private String redisLoaderToken;
    @Autowired
    private SyncDataService syncDataService;
    @Autowired
    private ServerNodeInfo currentServerNodeInfo;
    @Autowired
    private DistributeLockService distributeLockService;


    @Scheduled(fixedRate = 1000 * 50)
    public void keepliveSchedule() {
        DistributeLockDTO lockDTO = DistributeLockDTO.builder()
            .key(RedisConstants.SERVER_ROLE_KEY)
            .value(redisLoaderToken)
            .expireUnit("EX")
            .expireTime(60)
            .build();
        boolean status = true;
        // 只读节点会尝试去抢占锁
        if (ServerRoleType.ONLY_READER.equals(currentServerNodeInfo.getRoleType()) || ServerRoleType.OTHER.equals(currentServerNodeInfo.getRoleType())) {
            distributeLockService.locked(lockDTO);
        } else {
            // 读写节点会定时去延续对锁的持有
            distributeLockService.sustain(lockDTO);
            // 只有等到初始化同步结束后才进行续命操作
            if (syncDataService.finishSyncStatus()) {
                syncDataService.setSyncStatusForSuition(CacheDataSyncStatus.SYNC_FINISH, 60, TimeUnit.SECONDS);
            }
        }
        log.info("【{}】 : server node name 【{}】 , and it is role is 【{}】", new Date(), currentServerNodeInfo.getName(), currentServerNodeInfo.getRoleType().getLabel());
    }

    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void syncDbDataToCache() {
        if (ServerRoleType.WRITEER_AND_READER.equals(currentServerNodeInfo.getRoleType())) {
            // todo 获取数据版本号，决定是否进行数据同步
            log.info("【{}】 同步数据还是不同步数据是一个问题", new Date());
        }
    }
}
