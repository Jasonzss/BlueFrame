package com.blue_core.core;

import cn.hutool.core.util.ArrayUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Author Jason
 * @CreationDate 2023/02/16 - 0:01
 * @Description ：
 */
@Deprecated
public class MethodParameter {

    private static final Annotation[] EMPTY_ANNOTATION_ARRAY = new Annotation[0];

    private volatile Parameter parameter;

    private final int index;

    private volatile Class<?> parameterType;

    private volatile Annotation[] parameterAnnotations;

    private volatile String parameterName;

    public MethodParameter(Parameter parameter, int index) {
        this.parameter = parameter;
        this.parameterAnnotations = ArrayUtil.isEmpty(parameter.getAnnotations()) ?
                EMPTY_ANNOTATION_ARRAY : parameter.getAnnotations();
        this.parameterName = parameterType.getName();
        this.parameterType = parameter.getType();
        this.index = index;
    }
}
