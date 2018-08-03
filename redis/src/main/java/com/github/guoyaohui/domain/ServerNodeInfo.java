package com.github.guoyaohui.domain;

import com.github.guoyaohui.domain.enums.ServerRoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 郭垚辉
 * @date 2018/08/03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerNodeInfo {

    /**
     * 服务节点名称
     */
    private String name;

    /**
     * 服务节点的角色类型
     */
    private ServerRoleType roleType;

}
