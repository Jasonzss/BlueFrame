package com.mvc_test.web;

import com.blue_core.web.servlet.DispatcherServlet;
import com.blue_core.web.servlet.handler.MappedInterceptor;
import com.blue_core.web.servlet.handler.SimpleHandlerMapping;
import com.blue_core.web.servlet.mvc.method.annotation.SimpleRequestMappingHandlerAdapter;
import com.google.common.collect.Lists;

import javax.servlet.ServletException;
import java.util.ArrayList;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 23:13
 * @Description ：
 */
public class MyDispatcherServlet extends DispatcherServlet {
    @Override
    public void init() throws ServletException {
        super.init();

        SimpleHandlerMapping simpleHandlerMapping = new SimpleHandlerMapping();
        simpleHandlerMapping.addHandler(new MyController());
        simpleHandlerMapping.addHandler(new YourController());
        MappedInterceptor mappedInterceptor = new MappedInterceptor(Lists.newArrayList("/t0").toArray(new String[0]), new MyInterceptor());
        simpleHandlerMapping.addInterceptor(mappedInterceptor);

        setHandlerMappings(Lists.newArrayList(simpleHandlerMapping));
        setHandlerAdapters(Lists.newArrayList(new SimpleRequestMappingHandlerAdapter()));
    }
}
