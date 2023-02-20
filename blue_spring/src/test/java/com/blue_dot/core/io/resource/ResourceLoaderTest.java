package com.blue_dot.core.io.resource;

import cn.hutool.core.io.IoUtil;
import com.blue_dot.core.io.DefaultResourceLoader;
import com.blue_dot.core.io.ResourceLoader;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author Jason
 * @CreationDate 2023/01/28 - 14:50
 * @Description ：
 */
public class ResourceLoaderTest {
    @Test
    public void testGetResource() throws IOException {
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource(ResourceLoader.CLASSPATH_URL_PREFIX + "test.xml");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void testGetResource2() throws IOException {
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("C:\\Users\\Lenovo\\Desktop\\阿里巴巴Java开发手册-笔记版.pdf");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void testGetResource3() throws IOException {
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("https://cdn.staticfile.org/vue/3.2.39/vue.global.prod.js");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }
}
