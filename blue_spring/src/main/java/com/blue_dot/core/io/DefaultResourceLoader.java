package com.blue_dot.core.io;

import cn.hutool.core.lang.Assert;
import com.blue_dot.core.io.resource.ClassPathResource;
import com.blue_dot.core.io.resource.FileSystemResource;
import com.blue_dot.core.io.resource.Resource;
import com.blue_dot.core.io.resource.UrlResource;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author Jason
 * @CreationDate 2023/01/11 - 17:19
 * @Description ：
 */
public class DefaultResourceLoader implements ResourceLoader{
    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "资源定位不能为空！");
        if (location.startsWith(CLASSPATH_URL_PREFIX)){
            //当资源定位以 classpath: 开头时，说明是类路径的定位，则返回类路径资源
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }else {
            try {
                //尝试获取url的信息，若获取不到说明location不是url，而可能是文件路径
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                return new FileSystemResource(location);
            }
        }
    }
}
