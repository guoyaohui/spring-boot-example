package com.github.guoyaohui.controller;

import com.github.guoyaohui.config.RedisScriptConfig;
import com.github.guoyaohui.constants.RedisConstants;
import com.github.guoyaohui.domain.DistributeLockDTO;
import java.util.UUID;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 郭垚辉
 * @date 2018/08/03
 */
@RestController
public class IndexController {


    @Autowired
    private RedisScript<Boolean> distributeLockScript;


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private String redisLoaderToken;

    @GetMapping("/")
    public String index() {
        return UUID.randomUUID().toString();
    }

    @GetMapping("/locked")
    public String test() {
        DistributeLockDTO lockDTO = DistributeLockDTO.builder()
            .opration(0)
            .key(RedisConstants.REDIS_KEY)
            .value(redisLoaderToken)
            .expireUnit("EX")
            .expireTime(60)
            .build();
        Object execute = redisTemplate.execute(distributeLockScript, lockDTO.getKeys(), lockDTO.getArgvs());
        return String.valueOf(execute);
    }

    @GetMapping("/sustain")
    public String sustain() {
        DistributeLockDTO lockDTO = DistributeLockDTO.builder()
            .opration(1)
            .key(RedisConstants.REDIS_KEY)
            .value(redisLoaderToken)
            .expireUnit("EX")
            .expireTime(60)
            .build();
        Boolean execute = redisTemplate.execute(distributeLockScript, lockDTO.getKeys(), lockDTO.getArgvs());
        return String.valueOf(execute);
    }

    @GetMapping("/del")
    public String del() {
        DistributeLockDTO lockDTO = DistributeLockDTO.builder()
            .opration(2)
            .key(RedisConstants.REDIS_KEY)
            .value(redisLoaderToken)
            .expireUnit("EX")
            .expireTime(60)
            .build();
        Object execute = redisTemplate.execute(distributeLockScript, lockDTO.getKeys(), lockDTO.getArgvs());
        return String.valueOf(execute);
    }

}
