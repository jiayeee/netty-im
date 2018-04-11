package org.it.learn.netty.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.security.PrivateKey;
import java.util.PriorityQueue;

/**
 * 请求模块
 * 
 * Created by lwz on 2017/06/22 09:52.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketModule {

    /**
     * 请求模块号
     * 
     * @return
     */
    short module();
}
