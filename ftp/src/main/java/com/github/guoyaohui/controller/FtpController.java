package com.github.guoyaohui.controller;

import com.github.guoyaohui.pool.FTPClientPoolManager;
import java.io.File;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 郭垚辉
 * @date 2018/08/06
 */
@RestController
public class FtpController {

    @Autowired
    private FTPClientPoolManager ftpClientPoolManager;


    @GetMapping("/upload")
    public String upload() {
        boolean b = ftpClientPoolManager.uploadFile(new File("C:\\Develop_Env\\C_Home\\Cygwin\\etc\\mail\\sendmail.cf"), "/home/dljk/adfa");
        System.out.println(b);
        return UUID.randomUUID().toString();
    }

    @GetMapping("/download")
    public String downLoad() {
        boolean b = ftpClientPoolManager.downloadFile(new File("C:\\Develop_Env\\C_Home\\Cygwin\\etc\\22222\\sendmail.cf"), "/home/dljk/adfa");
        return UUID.randomUUID().toString();
    }

}
