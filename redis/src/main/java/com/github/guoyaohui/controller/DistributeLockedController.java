package com.github.guoyaohui.controller;

import com.github.guoyaohui.constants.RedisConstants;
import com.github.guoyaohui.domain.dto.DistributeLockDTO;
import com.github.guoyaohui.service.DistributeLockService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 郭垚辉
 * @date 2018/08/03
 */
@RestController
public class DistributeLockedController {
    @Autowired
    private String redisLoaderToken;
    @Autowired
    private DistributeLockService distributeLockScript;

    @GetMapping("/")
    public String index() {
        return UUID.randomUUID().toString();
    }

    @GetMapping("/locked")
    public String test() {
        DistributeLockDTO lockDTO = DistributeLockDTO.builder()
            .key(RedisConstants.SERVER_ROLE_KEY)
            .value(redisLoaderToken)
            .expireUnit("EX")
            .expireTime(60)
            .build();
        return String.valueOf(distributeLockScript.locked(lockDTO));
    }

    @GetMapping("/sustain")
    public String sustain() {
        DistributeLockDTO lockDTO = DistributeLockDTO.builder()
            .key(RedisConstants.SERVER_ROLE_KEY)
            .value(redisLoaderToken)
            .expireUnit("EX")
            .expireTime(60)
            .build();
        return String.valueOf(distributeLockScript.locked(lockDTO));
    }

    @GetMapping("/del")
    public String del() {
        DistributeLockDTO lockDTO = DistributeLockDTO.builder()
            .key(RedisConstants.SERVER_ROLE_KEY)
            .value(redisLoaderToken)
            .build();
        return String.valueOf(distributeLockScript.locked(lockDTO));
    }

}
