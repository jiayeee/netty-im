package org.it.learn.netty.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求命令
 * 
 * Created by lwz on 2017/06/22 09:52.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketCommand {

    /**
     * 请求命令号
     * 
     * @return
     */
    short cmd();
}
