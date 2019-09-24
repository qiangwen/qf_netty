package com.qf.netty.util;

/**
 * @author: wenqiang
 * @date: 2019-09-17 16:11
 */
public class BeanHolder<T> {

    private T instance;

    public BeanHolder(T instance){
        this.instance = instance;
    }

    public T getInstance(){
        return instance;
    }

}
