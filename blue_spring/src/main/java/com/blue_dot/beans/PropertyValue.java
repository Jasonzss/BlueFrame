package com.blue_dot.beans;

/**
 * @Author Jason
 * @CreationDate 2023/01/08 - 21:38
 * @Description ：参数键值对
 */
public class PropertyValue {
    private String name;
    private Object value;

    private boolean isRef;

    public PropertyValue(String name, Object value) {
        this(name,value,false);
    }

    public PropertyValue(String name, Object value, boolean isRef) {
        this.name = name;
        this.value = value;
        this.isRef = isRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Class<?> getClazz(){
        return value.getClass();
    }

    public boolean isRef() {
        return isRef;
    }

    public void setRef(boolean ref) {
        isRef = ref;
    }

    @Override
    public String toString() {
        return "PropertyValue{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
