package com.blue_dot.beans.factory;

/**
 * @Author Jason
 * @CreationDate 2023/01/29 - 22:13
 * @Description ：标记类接口，实现该接口可以被Spring容器感知
 * 被感知以后可从Spring容器中获取自己想要的资源，具体获取哪些由子类自行实现
 */
public interface Aware {

}
