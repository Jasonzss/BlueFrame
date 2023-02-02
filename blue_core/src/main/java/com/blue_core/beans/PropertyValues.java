package com.blue_core.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jason
 * @CreationDate 2023/01/08 - 21:36
 * @Description ：参数集合
 */
public class PropertyValues {
    private List<PropertyValue> values = new ArrayList<>();

    public void addValue(PropertyValue value){
        for(PropertyValue v : values){
            if (v.getName().equals(value.getName())) {
                v.setValue(value.getValue());
                return;
            }
        }

        values.add(value);
    }

    public PropertyValue getValue(String valueName){
        for (PropertyValue value : values) {
            if (value.getName().equals(valueName)) {
                return value;
            }
        }
        return null;
    }

    public PropertyValue[] getValues() {
        return values.toArray(new PropertyValue[0]);
    }

    public void setValues(List<PropertyValue> values){
        this.values = values;
    }

    public int size(){
        return values.size();
    }

    @Override
    public String toString() {
        return "PropertyValues{" +
                "values=" + values +
                '}';
    }
}
