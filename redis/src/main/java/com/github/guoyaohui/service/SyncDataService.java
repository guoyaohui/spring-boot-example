package com.github.guoyaohui.service;

import com.github.guoyaohui.constants.RedisConstants;
import com.github.guoyaohui.domain.ServerNodeInfo;
import com.github.guoyaohui.domain.enums.CacheDataSyncStatus;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * @author 郭垚辉
 * @date 2018/08/03
 */
@Component
public class SyncDataService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ServerNodeInfo currentServerNodeInfo;
    @Autowired
    private DistributeLockService distributeLockService;
    ValueOperations<String, String> operations;

    @PostConstruct
    public void init() {
        operations = redisTemplate.opsForValue();
    }

    /**
     * 设置同步状态
     */
    public void setSyncStatusIfAbsent(CacheDataSyncStatus status) {
        operations.setIfAbsent(RedisConstants.SYNC_DATA_STATUS_KEY_ONLY_FOR_INIT, String.valueOf(status.getIndex()));
    }

    public void setSyncStatusForSuition(CacheDataSyncStatus status, long timeout, TimeUnit unit) {
        operations.set(RedisConstants.SYNC_DATA_STATUS_KEY_ONLY_FOR_INIT, String.valueOf(status.getIndex()), timeout, unit);
    }

    /**
     * 获取同步结束状态
     */
    public boolean finishSyncStatus() {
        String value = operations.get(RedisConstants.SYNC_DATA_STATUS_KEY_ONLY_FOR_INIT);
        if (StringUtils.isBlank(value)) {
            return false;
        } else {
            return (Integer.valueOf(value) == CacheDataSyncStatus.SYNC_FINISH.getIndex());
        }
    }

}
