package com.github.guoyaohui.service;

import com.github.guoyaohui.domain.ServerNodeInfo;
import com.github.guoyaohui.domain.dto.DistributeLockDTO;
import com.github.guoyaohui.domain.enums.ServerRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

/**
 * 一个服务节点只有一种情况会丢失锁：超时，或者是服务节点宕机
 *
 * @author 郭垚辉
 * @date 2018/08/03
 */
@Service
public class DistributeLockService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ServerNodeInfo currentServerNodeInfo;
    @Autowired
    private RedisScript<Boolean> distributeLockScript;

    public String locked(DistributeLockDTO lockDTO) {
        lockDTO.setOpration(0);
        Boolean success = executeDistributeLockScript(lockDTO);
        if (success) {
            currentServerNodeInfo.setRoleType(ServerRoleType.WRITEER_AND_READER);
        } else {
            currentServerNodeInfo.setRoleType(ServerRoleType.ONLY_READER);
        }
        return String.valueOf(success);
    }

    public String sustain(DistributeLockDTO lockDTO) {
        lockDTO.setOpration(1);
        Boolean success = executeDistributeLockScript(lockDTO);
        if (success) {
            currentServerNodeInfo.setRoleType(ServerRoleType.WRITEER_AND_READER);
        } else {
            currentServerNodeInfo.setRoleType(ServerRoleType.ONLY_READER);
        }
        return String.valueOf(success);
    }

    public String del(DistributeLockDTO lockDTO) {
        lockDTO.setOpration(2);
        return String.valueOf(executeDistributeLockScript(lockDTO));
    }

    private Boolean executeDistributeLockScript(DistributeLockDTO lockDTO) {
        return redisTemplate.execute(distributeLockScript, lockDTO.getKeys(), lockDTO.getArgvs());
    }

}
