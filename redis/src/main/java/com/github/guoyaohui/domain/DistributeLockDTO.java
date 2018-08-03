package com.github.guoyaohui.domain;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 郭垚辉
 * @date 2018/08/03
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributeLockDTO {

    /**
     * 操作
     * 加锁 0
     * 维持锁 1
     * 释放锁 2
     */
    private int opration = -1;


    /**
     * redis中的键
     */
    private String key;

    /**
     * redis中的值
     */
    private String value;

    /**
     * 超时单位
     * EX 秒
     * PX 毫秒
     */
    private String expireUnit;

    /**
     * 超时时间
     */
    private int expireTime;

    public List<String> getKeys() {
        if (StringUtils.isNoneBlank(key)) {
            return Arrays.asList(key);
        }
        throw new RuntimeException("key is 【" + key + "】 must be not blank !!!");
    }

    public String[] getArgvs() {
        if (opration < 0) {
            throw new RuntimeException("opration 【" + opration + "】 must be larger than -1");
        }

        if (StringUtils.isBlank(value)) {
            throw new RuntimeException("value 【" + value + "】 must be not null");
        }

        if (opration == 0 || opration == 1) {
            // 加锁 或者是延续锁
            String expireTimeStr;
            if (expireTime < 0) {
                throw new RuntimeException("expireTime 【" + expireTime + "】 must be larger than -1");
            } else {
                expireTimeStr = String.valueOf(expireTime);
            }
            boolean passExpireUnit = StringUtils.isNoneBlank(expireUnit) && (expireUnit.equals("EX") || expireUnit.equals("PX"));
            if (!passExpireUnit) {
                throw new RuntimeException("exipre unit is 【" + expireUnit + "】 , but it must be EX or PX");
            }
            return Arrays.asList(String.valueOf(opration), value, expireUnit, expireTimeStr).toArray(new String[0]);
        } else {
            // 解锁
            return Arrays.asList(String.valueOf(opration), value).toArray(new String[0]);
        }
    }
}
