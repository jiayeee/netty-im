package org.it.learn.netty.common;

import org.it.learn.netty.annotation.SocketCommand;
import org.it.learn.netty.annotation.SocketModule;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * Created by lwz on 2017/06/22 11:38.
 */
@Component
public class Scanner implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<?>[] interfaces = bean.getClass().getInterfaces();

        if (interfaces != null) {
            for (int i = 0; i < interfaces.length; ++i) {
                Class<?> clazz = interfaces[i];

                SocketModule socketModule = clazz.getAnnotation(SocketModule.class);

                if (socketModule != null) {
                    Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);

                    if (methods != null) {
                        for (int j = 0; j < methods.length; ++j) {
                            Method method = methods[j];
                            SocketCommand socketCommand = method.getAnnotation(SocketCommand.class);
                            if (socketCommand != null) {

                                short module = socketModule.module();
                                short cmd = socketCommand.cmd();

                                Invoker invoker = Invoker.valueOf(bean, method);
                                InvokerHolder.addInvoker(module, cmd, invoker);
                            }
                        }
                    }
                }
            }
        }

        return bean;
    }
}
