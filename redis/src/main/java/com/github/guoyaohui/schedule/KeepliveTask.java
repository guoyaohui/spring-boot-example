package com.github.guoyaohui.schedule;

import com.github.guoyaohui.constants.RedisConstants;
import com.github.guoyaohui.domain.ServerNodeInfo;
import com.github.guoyaohui.domain.dto.DistributeLockDTO;
import com.github.guoyaohui.domain.enums.ServerRoleType;
import com.github.guoyaohui.service.DistributeLockService;
import java.util.Date;
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
    private DistributeLockService distributeLockScript;
    @Autowired
    private ServerNodeInfo currentServerNodeInfo;
    @Autowired
    private DistributeLockService distributeLockService;

    @Scheduled(fixedRate = 1000 * 50)
    public void keepliveSchedule() {
        DistributeLockDTO lockDTO = DistributeLockDTO.builder()
            .key(RedisConstants.REDIS_KEY)
            .value(redisLoaderToken)
            .expireUnit("EX")
            .expireTime(60)
            .build();
        // 只读节点会尝试去抢占锁
        if (ServerRoleType.ONLY_READER.equals(currentServerNodeInfo.getRoleType()) || ServerRoleType.OTHER.equals(currentServerNodeInfo.getRoleType())) {
            distributeLockService.locked(lockDTO);
        } else {
            // 读写节点会定时去延续对锁的持有
            distributeLockScript.sustain(lockDTO);
        }
        log.info("【{}】 : server node name 【{}】 , and it is role is 【{}】", new Date(), currentServerNodeInfo.getName(), currentServerNodeInfo.getRoleType().getLabel());
    }
}
