package com.github.guoyaohui.business;

import com.github.guoyaohui.domain.ServerNodeInfo;
import com.github.guoyaohui.service.DistributeLockService;
import com.github.guoyaohui.service.SyncDataService;
import com.github.guoyaohui.utils.DateUtils;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 郭垚辉
 * @date 2018/08/03
 */
@Slf4j
@Component
public class LoaderTask implements Runnable {


    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ServerNodeInfo currentServerNodeInfo;
    @Autowired
    private DistributeLockService distributeLockService;
    @Autowired
    private SyncDataService syncDataService;
    private AtomicInteger integer = new AtomicInteger(0);


    @Override
    public void run() {
        boolean status = syncDataService.finishSyncStatus();
        while (true) {
            if (status) {
                // todo your work
                log.info("开始执行任务【{}】...", integer.getAndAdd(1));
                DateUtils.sleep(1000 * 60 * 1);
            } else {
                log.info("【{}】 缓存数据状态无法使用，请等待缓存数据初始化完毕 【{}】", new Date(), status);
                DateUtils.sleep(1000 * 60 * 1);
                status = syncDataService.finishSyncStatus();
                log.info("【{}】 test sync status is 【{}】", new Date(), status);
                continue;
            }
        }
    }
}
