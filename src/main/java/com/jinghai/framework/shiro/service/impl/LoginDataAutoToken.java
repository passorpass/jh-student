package com.jinghai.framework.shiro.service.impl;

import com.jinghai.framework.shiro.service.DataAutoToken;
import org.apache.shiro.authc.UsernamePasswordToken;

public class LoginDataAutoToken extends UsernamePasswordToken implements DataAutoToken {
    public LoginDataAutoToken(final String username, final String password) {
        super(username, password);
    }

    @Override
    public String getName() {
        return "LOGIN";
    }
}
