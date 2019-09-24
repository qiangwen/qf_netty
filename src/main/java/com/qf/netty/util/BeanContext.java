package com.qf.netty.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: wenqiang
 * @date: 2019-09-17 16:05
 */
public class BeanContext {

    private static final BeanContext beanContext = new BeanContext();

    private Map<String,BeanHolder<?>> singletonBeanHolderMap = new ConcurrentHashMap<>(); //存放单例模式

    private BeanContext(){}

    public static BeanContext getContext(){
        return beanContext;
    }

    public <T> T getSingleInstance(Class<T> cls){
        String clsName =  cls.getName();
        BeanHolder<?> beanHolder = singletonBeanHolderMap.get(clsName);
        if(beanHolder == null){
            synchronized (this){
                if(beanHolder == null){
                    T instance = BeanFactory.getInstance(cls);
                    beanHolder = new BeanHolder<>(instance);
                    singletonBeanHolderMap.put(clsName,beanHolder);
                }
            }
        }
        return (T) beanHolder.getInstance();
    }

}
