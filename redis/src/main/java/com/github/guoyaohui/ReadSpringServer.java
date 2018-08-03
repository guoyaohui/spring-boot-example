package com.github.guoyaohui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 郭垚辉
 * @create_time 2018/7/21
 */
@EnableScheduling
@SpringBootApplication
public class ReadSpringServer {


    public static void main(String[] args) {
        SpringApplication.run(ReadSpringServer.class, args);
    }


}
