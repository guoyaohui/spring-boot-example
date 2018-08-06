package com.github.guoyaohui.config;

import com.github.guoyaohui.pool.FTPClientPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 郭垚辉
 * @date 2018/08/06
 */
@Configuration
public class FTPConfiguration {

    @Autowired
    private FTPClientPoolProperties ftpClientPoolProperties;
    @Autowired
    private FTPClientProperties ftpClientProperties;

    @Bean
    public FTPClientPoolManager ftpClientPoolManager() {
        return new FTPClientPoolManager(ftpClientProperties, ftpClientPoolProperties);
    }

}
