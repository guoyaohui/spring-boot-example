package com.github.guoyaohui.dto;

import com.github.guoyaohui.constant.FTPConstant;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;

/**
 * @author 郭垚辉
 * @date 2018/08/06
 */
public class FTPClientWrapper extends FTPClient {

    /**
     * 将当前的目录切换到根目录下
     */
    public boolean changeRootDirectory() throws IOException {
        return super.changeWorkingDirectory("/");
    }

    /**
     * 将本地文件发送到ftp服务器上
     *
     * @param file 待存储的文件
     * @param path 存储在ftp上的路径
     * @return 存储状态
     */
    public boolean uploadFile(File file, String path) throws IOException {
        if (checkParameterValid(file, path)) {
            try (InputStream stream = new FileInputStream(file)) {
                recurseDirectory(path.split(FTPConstant.SEPARATOR));
                return super.storeFile(file.getName(), stream);
            }
        }
        return false;
    }

    /**
     * 将ftp服务器上的文件下载到本地
     *
     * @param file 文件
     * @param path 远程服务器上的ftp文件
     */
    public boolean download(File file, String path) throws IOException {
        if (checkParameterValid(file, path)) {
            try (OutputStream stream = new FileOutputStream(file)) {
                recurseDirectory(path.split(FTPConstant.SEPARATOR));
                return super.retrieveFile(file.getName(), stream);
            }
        }
        return false;
    }

    /**
     * 校验参数有效性
     */
    private boolean checkParameterValid(File file, String path) {
        if (StringUtils.isBlank(path)) {
            return false;
        }
        return file != null;
    }

    /**
     * 循环递归目录
     * 如要要进入的目录不存在则创建该目录
     */
    private void recurseDirectory(String[] paths) throws IOException {
        for (String path : paths) {
            if (StringUtils.isNoneBlank(path)) {
                while (!super.changeWorkingDirectory(path)) {
                    boolean b = super.makeDirectory(path);
                    System.out.println(b);
                }
            }
        }
    }

}
