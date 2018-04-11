package org.it.learn.netty.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * Created by lwz on 2017/06/22 10:17.
 */
public class Invoker {

    private Object target;

    private Method method;

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public static Invoker valueOf(Object target, Method method) {
        Invoker invoker = new Invoker();
        invoker.setTarget(target);
        invoker.setMethod(method);
        return invoker;
    }

    public Object invoke(Object[] args) {

        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
