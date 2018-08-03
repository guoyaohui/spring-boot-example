package com.github.guoyaohui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author 郭垚辉
 * @create_time 2018/7/21
 */
@SpringBootApplication
public class ReadSpringServer {


    public static void main(String[] args) {
        SpringApplication.run(ReadSpringServer.class, args);
    }




}
