package com.leeyen.cglib.interceptor;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class DebugMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println("before method " + method.getName());
        methodProxy.invokeSuper(o, objects);
        System.out.println("affter method " + method.getName());

        return objects;
    }
}
