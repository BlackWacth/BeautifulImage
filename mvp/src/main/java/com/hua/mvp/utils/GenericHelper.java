package com.hua.mvp.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 */
public class GenericHelper {

    /**
     * 获取父类第一个泛型类型。
     * @param klass
     * @param <T>
     * @return
     */
    public static <T> Class<T> getViewClass(Class<?> klass) {
        /**
         * getGenericSuperclass 获取带有泛型的父类；
         * Type是 Java 编程语言中所有类型的公共高级接口。
         * 它们包括原始类型、参数化类型(泛型)、数组类型、类型变量和基本类型。
         */
        Type type = klass.getGenericSuperclass();
        if(type == null || !(type instanceof ParameterizedType)){
            return null;
        }

        //参数化类型(泛型)
        ParameterizedType parameterizedType = (ParameterizedType) type;
        //获取参数化类型的数组，泛型可能有多个
        Type[] types = parameterizedType.getActualTypeArguments();
        if(types == null || types.length == 0) {
            return null;
        }
        return (Class<T>) types[0];
    }

}
