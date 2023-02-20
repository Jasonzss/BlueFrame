package com.blue_dot.beans.factory;

import com.blue_dot.beans.BeansException;
import com.blue_dot.beans.PropertyValue;
import com.blue_dot.beans.PropertyValues;
import com.blue_dot.beans.factory.config.BeanDefinition;
import com.blue_dot.beans.factory.config.BeanFactoryPostProcessor;
import com.blue_dot.core.io.DefaultResourceLoader;
import com.blue_dot.core.io.resource.Resource;
import com.blue_dot.utils.StringValueResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author Jason
 * @CreationDate 2023/02/01 - 23:24
 * @Description ：
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {
    /**
     * Default placeholder prefix: {@value}
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /**
     * Default placeholder suffix: {@value}
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    /**
     * 资源定位
     */
    private String location;


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 加载属性文件
        try {
            //导入location的资源
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            //遍历所有的BeanDefinition
            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

                //遍历所有的（被Spring记录的）属性
                PropertyValues propertyValues = beanDefinition.getProperties();
                for (PropertyValue propertyValue : propertyValues.getValues()) {
                    Object value = propertyValue.getValue();
                    if (!(value instanceof String)) {
                        //排除字符串以外的属性，占位符只能是String类型的
                        continue;
                    }

                    //解析字符串
                    value = resolvePlaceholder((String) value, properties);

                    //加入属性，已存在的则覆盖
                    propertyValues.addValue(new PropertyValue(propertyValue.getName(),value));
                }

                // 向容器中添加字符串解析器，供解析@Value注解使用
                StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
                beanFactory.addEmbeddedValueResolver(valueResolver);
            }
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }


    private String resolvePlaceholder(String value, Properties properties) {
        StringBuilder buffer = new StringBuilder(value);
        int startIdx = value.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        int stopIdx = value.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);

        //判断属性的value是否为占位符
        if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
            //去掉value的占位符，获得主要value所代表的值：${name} -> name
            String propKey = value.substring(startIdx + 2, stopIdx);
            //根据key从资源中获取值value
            String propVal = properties.getProperty(propKey);
            //真值替换占位符
            buffer.replace(startIdx, stopIdx + 1, propVal);
        }
        return buffer.toString();
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {
        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String strVal) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal, properties);
        }

    }
}
