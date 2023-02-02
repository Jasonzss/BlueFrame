package com.blue_core.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.blue_core.beans.BeansException;
import com.blue_core.beans.PropertyValue;
import com.blue_core.beans.PropertyValues;
import com.blue_core.beans.factory.config.BeanDefinition;
import com.blue_core.beans.factory.config.BeanReference;
import com.blue_core.beans.factory.config.GenericBeanDefinition;
import com.blue_core.beans.factory.support.AbstractBeanDefinitionReader;
import com.blue_core.beans.factory.support.BeanDefinitionRegistry;
import com.blue_core.context.annotation.ClassPathBeanDefinitionScanner;
import com.blue_core.core.io.ResourceLoader;
import com.blue_core.core.io.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author Jason
 * @CreationDate 2023/01/11 - 17:53
 * @Description ：解析xml中读取到的配置信息成BeanDefinition
 * 核心的解析逻辑在 doLoadBeanDefinitions 方法中
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlBeanDefinitionReader.class);

    public static final String BEAN_ELEMENT = "bean";
    public static final String COMPONENT_SCAN_ELEMENT = "component-scan";

    public static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
    public static final String PROPERTY_ATTRIBUTE = "property";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String REF_ELEMENT = "ref";
    public static final String ID_ATTRIBUTE = "id";
    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";
    public static final String SCOPE_ATTRIBUTE = "scope";





    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            InputStream is = resource.getInputStream();
            doLoadBeanDefinitions(is);
        } catch (IOException | ClassNotFoundException e) {
            throw new BeansException("IO异常，无法读取到资源文件", e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        //将资源定位转换成资源类
        Resource resource = getResourceLoader().getResource(location);
        loadBeanDefinitions(resource);
    }

    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
        Document document = XmlUtil.readXML(inputStream);
        //得到XML的根节点
        Element rootElement = document.getDocumentElement();
        NodeList childNodes = rootElement.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            // 判断元素，过滤掉非元素节点
            if (!(childNodes.item(i) instanceof Element)) {
                continue;
            }

            // 判断对象
            //TODO 这里写的还是太耦合了，需要改进

            Element bean = (Element) childNodes.item(i);

            switch (bean.getTagName()){
                case BEAN_ELEMENT:readTagBean(bean);break;
                case COMPONENT_SCAN_ELEMENT:readTagComponentScan(bean);break;
                default:
                    throw new BeansException("未知的xml标签");
            }

        }
    }

    /**
     * 解析Bean标签节点，并将BeanDefinition放入注册表中
     * @param beanTag bean标签节点
     */
    private void readTagBean(Element beanTag){
        String beanName = beanTag.getAttribute(ID_ATTRIBUTE);
        String classPath = beanTag.getAttribute(CLASS_ATTRIBUTE);

        Class<?> clazz;
        try {
            clazz = Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            throw new BeansException("找不到的类 ：["+classPath+"]");
        }

        //将读取到的信息放入BeanDefinition中
        BeanDefinition beanDefinition = new GenericBeanDefinition(clazz);

        //1. 注入属性
        beanDefinition.setInitMethodName(beanTag.getAttribute(INIT_METHOD_ATTRIBUTE));
        beanDefinition.setDestroyMethodName(beanTag.getAttribute(DESTROY_METHOD_ATTRIBUTE));
        beanDefinition.setScope(beanTag.getAttribute(SCOPE_ATTRIBUTE));

        NodeList childNodes = beanTag.getChildNodes();
        //2. 解析、读取属性标签并注入
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (!(childNodes.item(i) instanceof Element)) {
                continue;
            }
            Element node = (Element) childNodes.item(i);

            if ((PROPERTY_ATTRIBUTE.equals(node.getTagName()))){
                //解析property标签
                String attrName = node.getAttribute(NAME_ATTRIBUTE);
                String attrValue = node.getAttribute(VALUE_ATTRIBUTE);
                String attrRef = node.getAttribute(REF_ELEMENT);

                // 获取属性值：引入对象、值对象
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                // 创建属性信息
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getProperties().addValue(propertyValue);
            }else if (CONSTRUCTOR_ARG_ELEMENT.equals(node.getTagName())){
                //解析constructor-arg标签
                //解析property标签
                //TODO 构造器参数后面应该还要加参数名字，这样使用更加人性化。所以下面这个attrName会派上用场
                //TODO 构造器参数标签解析的功能还有些问题，目前先不管，回头再改
                String attrName = node.getAttribute(NAME_ATTRIBUTE);
                String attrValue = node.getAttribute(VALUE_ATTRIBUTE);
                String attrRef = node.getAttribute(REF_ELEMENT);

                // 获取属性值：引入对象、值对象
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                beanDefinition.getConstructorArgumentValues().addArgumentValue(value);
            }
            // 其他bean的子标签的操作
        }

        //TODO 在这里写关于BeanName的逻辑是不合理的，回头需要统一在一个地方换
        //如果名字为空则使用小写首字母的类名称作为beanName
        beanName = "".equals(beanName) ? StrUtil.lowerFirst(beanDefinition.getBeanClass().getSimpleName()) : beanName;

        if (getRegistry().containsBeanDefinitionName(beanName)) {
            throw new BeansException("beanName[" + beanName + "]已存在，无法再进行注册");
        }
        // 注册 BeanDefinition
        getRegistry().registerBeanDefinition(beanName, beanDefinition);
    }

    /**
     * 处理ComponentScan配置
     */
    private void readTagComponentScan(Element componentScan){
        if (componentScan != null){
            String scanPath = componentScan.getAttribute(BASE_PACKAGE_ATTRIBUTE);
            if (!StrUtil.isNotEmpty(scanPath)){
                throw new BeansException("component-scan配置的扫描路径不能为空或不设置");
            }

            //切开配置的多个扫描路劲
            String[] basePackages = StrUtil.splitToArray(scanPath, ',');
            //进行扫描
            ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
            scanner.doScan(basePackages);
        }
    }
}
