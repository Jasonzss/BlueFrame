package com.blue_core.aop;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 17:27
 * @Description ：封装了Aop代理的目标类，有获取其基本信息的能力
 */
public class TargetSource {
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 返回此 TargetSource 包装的目标类型。
     * 可以返回null ，尽管TargetSource的某些用法可能只适用于预定的目标类。
     * @return 此TargetSource返回的目标类型
     */
    public Class<?>[] getTargetClass(){
        return this.target.getClass().getInterfaces();
    }

    /**
     * 返回目标对象实例
     * @return 包含 接入点 的目标对象
     */
    public Object getTarget() {
        return this.target;
    }
}
