package com.github.guoyaohui.utils;

/**
 * @author 郭垚辉
 * @date 2018/08/03
 */
public class DateUtils {

    /**
     * 时间毫秒
     */
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
