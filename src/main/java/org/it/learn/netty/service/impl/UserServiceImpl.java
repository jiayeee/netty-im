package org.it.learn.netty.service.impl;

import org.it.learn.netty.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Created by lwz on 2017/06/22 09:51.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Override
    public void getInfo() {
        System.out.println("getInfo");
    }

    @Override
    public void login() {
        System.out.println("login");
    }
}
