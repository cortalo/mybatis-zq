package com.zhengqing.mybatis.binding;

import com.zhengqing.mybatis.session.Configuration;

import java.lang.reflect.Proxy;

public class MapperProxyFactory {

    // 其返回的对象obj，当其调用obj.f(args)时，实际会调用MapperProxy的invoke方法，即invoke(obj, f, args)
    public static <T> T getProxy(Class<T> mapperClass, Configuration configuration) {
        return (T) Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass},
                new MapperProxy(configuration, mapperClass));
    }

}
