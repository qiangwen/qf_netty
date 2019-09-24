package com.qf.netty.util;

import java.lang.reflect.InvocationTargetException;

/**
 * @author: wenqiang
 * @date: 2019-09-17 15:33
 */
public class BeanFactory {


    public static <T> T getInstance(Class<T> cls,Class<?>[] paramTypes,Object[] params){
        try {
           return cls.getConstructor(paramTypes).newInstance(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getInstance(Class<T> cls){
        try {
            return cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}
