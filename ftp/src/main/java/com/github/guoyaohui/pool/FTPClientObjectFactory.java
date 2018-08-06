package com.github.guoyaohui.pool;


import com.github.guoyaohui.config.FTPClientProperties;
import com.github.guoyaohui.dto.FTPClientWrapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * ftp连接池
 *
 * @author 郭垚辉
 * @date 2018/08/06
 */
@Slf4j
public class FTPClientObjectFactory extends BasePooledObjectFactory<FTPClientWrapper> {

    private FTPClientProperties properties;

    public FTPClientObjectFactory(FTPClientProperties properties) {
        this.properties = properties;
    }

    @Override
    public FTPClientWrapper create() throws Exception {
        FTPClientWrapper client = new FTPClientWrapper();
        client.connect(properties.getHost(), properties.getPort());
        if (properties.isConnectionMode()) {
            client.enterLocalActiveMode();
        } else {
            client.enterLocalPassiveMode();
        }
        if (properties.isFileType()) {
            client.setFileType(FTP.BINARY_FILE_TYPE);
        } else {
            client.setFileType(FTP.ASCII_FILE_TYPE);
        }
        client.setControlEncoding(properties.getEncoding());
        client.setAutodetectUTF8(properties.isAutodetect());
        client.login(properties.getUsername(), properties.getPassword());
        return client;
    }

    @Override
    public PooledObject<FTPClientWrapper> wrap(FTPClientWrapper ftpClient) {
        return new DefaultPooledObject<>(ftpClient);
    }

    @Override
    public boolean validateObject(PooledObject<FTPClientWrapper> p) {
        FTPClientWrapper wrapper = p.getObject();
        try {
            return wrapper.sendNoOp();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("when ftp client send no option to ftp server, occur some error {}", e);
        }
        return false;
    }

    @Override
    public void destroyObject(PooledObject<FTPClientWrapper> ftpClientPooledObject) throws Exception {
        FTPClientWrapper client = ftpClientPooledObject.getObject();
        if (client != null && client.isAvailable() && client.isConnected()) {
            client.logout();
            client.disconnect();
        }
    }
}
