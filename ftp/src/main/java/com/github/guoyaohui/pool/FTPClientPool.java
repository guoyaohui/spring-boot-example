package com.github.guoyaohui.pool;

import com.github.guoyaohui.config.FTPClientPoolProperties;
import com.github.guoyaohui.dto.FTPClientWrapper;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @author 郭垚辉
 * @date 2018/08/06
 */
public class FTPClientPool extends GenericObjectPool<FTPClientWrapper> {

    public FTPClientPool(PooledObjectFactory<FTPClientWrapper> factory, FTPClientPoolProperties config) {
        super(factory, config);
    }
}
