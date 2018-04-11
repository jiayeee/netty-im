package org.it.learn.netty.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lwz on 2017/06/22 10:23.
 */
public class InvokerHolder {

    private static final Logger                    LOGGER   = LoggerFactory.getLogger(InvokerHolder.class);

    private static Map<Short, Map<Short, Invoker>> invokers = new HashMap<>();

    public static void addInvoker(short module, short cmd, Invoker invoker) {
        if (getInvoker(module, cmd) != null) {
            LOGGER.error("重复注册执行器, module : {}, cmd : {}.", module, cmd);
        }

        Map<Short, Invoker> map = invokers.get(module);
        if (map == null) {
            map = new HashMap<>();
            invokers.put(module, map);
        }

        map.put(cmd, invoker);
    }

    public static Invoker getInvoker(short module, short cmd) {

        Map<Short, Invoker> map = invokers.get(module);
        if (map != null) {
            return map.get(cmd);
        }

        return null;
    }
}
