package com.blue_core.beans;

/**
 * @Author Jason
 * @CreationDate 2023/01/10 - 15:50
 * @Description ：Bean异常
 */
public class BeansException extends RuntimeException {
    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
