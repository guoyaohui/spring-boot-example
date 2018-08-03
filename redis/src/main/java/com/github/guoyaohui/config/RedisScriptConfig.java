package com.github.guoyaohui.config;

import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @author 郭垚辉
 * @date 2018/08/03
 */
@Configuration
public class RedisScriptConfig {


    @Bean
    public RedisScript<Boolean> distributeLockScript() {
        DefaultRedisScript<Boolean> script = new DefaultRedisScript<>();
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("distributeLock.lua")));
        script.setResultType(Boolean.class);
        return script;
    }

    @Bean
    public String redisLoaderToken() {
        String token = UUID.randomUUID().toString();
        return token;
    }

}
