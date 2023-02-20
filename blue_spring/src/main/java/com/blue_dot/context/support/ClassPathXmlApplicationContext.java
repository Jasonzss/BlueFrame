package com.blue_dot.context.support;

/**
 * @Author Jason
 * @CreationDate 2023/01/06 - 17:07
 * @Description ：
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{
    String[] xmlConfigLocations;

    public ClassPathXmlApplicationContext(String xmlConfigLocations) {
        this(new String[]{xmlConfigLocations});
    }

    public ClassPathXmlApplicationContext(String[] xmlConfigLocations) {
        this.xmlConfigLocations = xmlConfigLocations;
        //创建XmlApplicationContext完成后刷新一遍
        refresh();
    }

    @Override
    protected String[] getXmlConfigLocations() {
        return xmlConfigLocations;
    }
}
