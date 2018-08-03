package com.github.guoyaohui.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 只有在所有节点都是第一次启动时才有意义
 *
 * @author 郭垚辉
 * @date 2018/08/03
 */
@Getter
@AllArgsConstructor
public enum CacheDataSyncStatus {

    SYNCING(1, "同步中..."),
    SYNC_FINISH(2, "同步完成"),
    OTHER(100, "其他状态");

    private int index;
    private String label;

}
