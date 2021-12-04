package com.leeyen.jdk.factory;

import com.leeyen.jdk.proxy.DebugInvocationHandler;

import java.lang.reflect.Proxy;

public class JdkProxyFactory {
    public static Object getProxy(Object target){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new DebugInvocationHandler(target)
        );
    }
}
