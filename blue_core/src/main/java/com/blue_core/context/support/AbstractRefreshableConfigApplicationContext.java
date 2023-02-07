package com.blue_core.context.support;

import cn.hutool.core.lang.Assert;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 17:06
 * @Description ：继承自AbstractRefreshableApplicationContext类，在父类的名称上多加了一个Config，表示这个类负责读取配置
 */
public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext{
    private String[] configLocations;

    public String[] getConfigLocations() {
        return (this.configLocations != null ? this.configLocations : getDefaultConfigLocations());
    }

    public void setConfigLocations(String[] locations) {
        if (locations != null) {
            Assert.noNullElements(locations, "Config locations must not be null");
            this.configLocations = new String[locations.length];
            for (int i = 0; i < locations.length; i++) {
                this.configLocations[i] = locations[i].trim();
            }
        } else {
            this.configLocations = null;
        }
    }

    public void setConfigLocation(String configLocation){
        String[] configLocations = new String[1];
        configLocations[0] = configLocation;
        setConfigLocations(configLocations);
    }

    //TODO
    private String resolvePath(String location) {
        return null;
    }

    protected String[] getDefaultConfigLocations() {
        return null;
    }
}
