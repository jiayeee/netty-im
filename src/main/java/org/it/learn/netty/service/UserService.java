package org.it.learn.netty.service;

import org.it.learn.netty.annotation.SocketCommand;
import org.it.learn.netty.annotation.SocketModule;

/**
 * Created by lwz on 2017/06/22 09:51.
 */
@SocketModule(module = 1)
public interface UserService {

    @SocketCommand(cmd = 1)
    public void getInfo();

    @SocketCommand(cmd = 2)
    public void login();
}
