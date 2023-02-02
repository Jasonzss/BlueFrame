package com.blue_core.core.io;

import org.yaml.snakeyaml.Yaml;

import java.util.Map;

/**
 * @Author Jason
 * @CreationDate 2023/01/08 - 20:23
 * @Description ：
 */
@Deprecated
public class YmlParser {
    public void readYml(String path){
        Yaml yaml=new Yaml();
        Map<String, Object> map = yaml.load(getClass().getClassLoader()
                        .getResourceAsStream(path));
    }
}
