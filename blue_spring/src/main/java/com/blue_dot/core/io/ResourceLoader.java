package com.blue_dot.core.io;

import com.blue_dot.core.io.resource.Resource;

/**
 * @Author Jason
 * @CreationDate 2023/01/11 - 17:17
 * @Description ：
 */
public interface ResourceLoader {
    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 根据资源定位获取资源
     * @param location 资源定位，目前分classPath、url、filePath三种
     */
    Resource getResource(String location);
}
