package com.blue_core.core.io.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author Jason
 * @CreationDate 2023/01/11 - 16:48
 * @Description ：资源的顶级抽象接口，往下有三种资源：url指向的资源、类路径指向的资源、文件系统指向的资源
 */
public interface Resource {
    InputStream getInputStream() throws IOException;
}
