package com.github.guoyaohui.config;

import com.github.guoyaohui.domain.ServerNodeInfo;
import com.github.guoyaohui.domain.enums.ServerRoleType;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
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

    @Bean
    public ServerNodeInfo currentServerNodeInfo() {
        ServerNodeInfo serverNodeInfo = new ServerNodeInfo();
        serverNodeInfo.setName(UUID.randomUUID().toString());
        serverNodeInfo.setRoleType(ServerRoleType.OTHER);
        return serverNodeInfo;
    }

}
