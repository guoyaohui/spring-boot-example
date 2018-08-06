package com.github.guoyaohui.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 郭垚辉
 * @date 2018/08/06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties("github.ftp")
public class FTPClientProperties {

    /**
     * 主机名
     */
    private String host;

    /**
     * 端口
     */
    private int port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 指定编码
     */
    private String encoding;

    /**
     * 是否采用自动检测编码
     */
    private boolean autodetect;

    /**
     * 二进制文件：{@link FTP#BINARY_FILE_TYPE}
     * 文本文件：{@link FTP#ASCII_FILE_TYPE}
     *
     * true 二进制
     * false 文本模式
     */
    private boolean fileType;

    /**
     * 谈谈主动模式和被动模式(针对于服务端来说)
     *
     * 1. ftp进行连接的过程中，会使用到两个端口，一个是控制端口，一个是数据端口。
     * 控制端口顾名思义就是：进行客户端和服务端的控制命令的传输
     * 数据端口则是真正用来传输数据的端口。
     *
     * 2. 主动模式
     * 客户端打开一个大于1023的端口，连接到服务端的控制端口(默认的控制端口为21)
     * 这时候服务端主动使用自己的20端口，去连接客户端产生的另外一个大于1023的端口，然后这两者建立起数据连接通道进行数据传输
     *
     * 3. 被动模式
     * 客户端打开一个大于1023的端口，连接到服务端的控制端口(默认的控制端口为21)
     * 这时候服务端产生一个大于1023的端口，告知给客户端，然后客户端在本地又生成一个大于1023的端口，然后由客户端请求建立连接，建立数据传输的通道。
     *
     * 4. 由于nat的存在
     * 当服务端无法正确定位到客户端的ip的时候，需要使用被动模式。
     * 例如，服务端在公网，客户端在私网，这时候需要使用被动模式。毕竟服务端不知道你这个172.16.21.186的玩意在哪里。
     *
     *
     * 主动模式：{@link org.apache.commons.net.ftp.FTPClient#ACTIVE_LOCAL_DATA_CONNECTION_MODE}
     * 被动模式：{@link org.apache.commons.net.ftp.FTPClient#PASSIVE_LOCAL_DATA_CONNECTION_MODE}
     *
     * connectionMode: true为主动模式 false被动模式
     *
     */
    private boolean connectionMode;

}
