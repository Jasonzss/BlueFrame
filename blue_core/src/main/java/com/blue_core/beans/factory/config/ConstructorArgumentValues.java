package com.blue_core.beans.factory.config;

import java.util.*;

/**
 * @Author Jason
 * @CreationDate 2023/02/01 - 20:03
 * @Description ：构造器参数
 */
public class ConstructorArgumentValues {

    private final List<Object> genericArgumentValues = new ArrayList<>();

    public void addArgumentValue(Object obj){
        genericArgumentValues.add(obj);
    }

    public Object[] getArgs(){
        return genericArgumentValues.toArray();
    }

    public void setGenericArgumentValues(Object[] args){
        if (args == null){
            return;
        }
        genericArgumentValues.addAll(Arrays.asList(args));
    }
}
