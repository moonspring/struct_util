package com.lzq.struct.util.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JDKProxy implements InvocationHandler {

    private Object object;

    public JDKProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] paramValues) throws Throwable {
        return ((RealHello)object).invoke();
    }
}
