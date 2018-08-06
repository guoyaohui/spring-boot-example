package com.github.guoyaohui.pool;

import com.github.guoyaohui.config.FTPClientPoolProperties;
import com.github.guoyaohui.config.FTPClientProperties;
import com.github.guoyaohui.dto.FTPClientWrapper;
import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 郭垚辉
 * @date 2018/08/06
 */
@Slf4j
public class FTPClientPoolManager {

    private FTPClientPool ftpClientPool;

    public FTPClientPoolManager(FTPClientProperties properties, FTPClientPoolProperties config) {
        FTPClientObjectFactory factory = new FTPClientObjectFactory(properties);
        ftpClientPool = new FTPClientPool(factory, config);
    }

    public boolean uploadFile(File file, String path) {
        FTPClientWrapper wrapper = null;
        try {
            if ((wrapper = ftpClientPool.borrowObject()) != null) {
                wrapper.changeRootDirectory();
                boolean success = wrapper.uploadFile(file, path);
                wrapper.changeRootDirectory();
                return success;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (wrapper != null) {

                ftpClientPool.returnObject(wrapper);
            }
        }
        return false;
    }

    public boolean downloadFile(File file, String path) {
        FTPClientWrapper wrapper = null;
        try {
            if ((wrapper = ftpClientPool.borrowObject()) != null) {
                return wrapper.download(file, path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (wrapper != null) {
                ftpClientPool.returnObject(wrapper);
            }
        }
        return false;
    }
}
