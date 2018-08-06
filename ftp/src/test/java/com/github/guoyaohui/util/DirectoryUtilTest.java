package com.github.guoyaohui.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 郭垚辉
 * @date 2018/08/06
 */
public class DirectoryUtilTest {

    @org.junit.Test
    public void createIfNeed() throws IOException {
        File file = DirectoryUtil.createDirectory(new File("D:\\234234234\\2342134g\\sadfa\\asdfadfaf\\asdfasdf\\asdfasdf\\a.txt"), true);
        OutputStream stream = new FileOutputStream(file);
        stream.write("hhhsadfasdf".getBytes());
        stream.close();
    }
}