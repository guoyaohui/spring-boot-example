package com.github.guoyaohui.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 服务节点
 *
 * @author 郭垚辉
 * @date 2018/08/03
 */
@Getter
@AllArgsConstructor
public enum ServerRoleType {

    WRITEER_AND_READER(1, "读写兼具的服务节点"),
    ONLY_READER(2, "单纯具有读数据的服务节点"),
    OTHER(100, "其他的类型节点");

    private int index;
    private String label;
}
