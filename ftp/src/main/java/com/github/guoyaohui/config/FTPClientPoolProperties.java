package com.github.guoyaohui.config;

import com.github.guoyaohui.dto.FTPClientWrapper;
import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 郭垚辉
 * @date 2018/08/06
 */
@Data

@Component
@ConfigurationProperties("github.ftp.pool")
public class FTPClientPoolProperties extends GenericObjectPoolConfig<FTPClientWrapper> {

    /**
     * 连接池中ftp的最大数量
     */
    private int maxTotal = DEFAULT_MAX_TOTAL;

    /**
     * 最大存货时间
     */
    private int maxIdle = DEFAULT_MAX_IDLE;

    /**
     *
     */
    private int minIdle = DEFAULT_MIN_IDLE;


}
