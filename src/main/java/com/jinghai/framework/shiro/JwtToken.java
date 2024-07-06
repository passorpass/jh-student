package com.jinghai.framework.shiro;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;


/**
 * 实现shiro的AuthenticationToken接口的类JwtToken
 *
 * @author xiaosongyue
 * @date 2021/01/21 15:41:20
 */
@Slf4j
public class JwtToken implements AuthenticationToken{

    private  String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() { //相当于用户名
        return token;
    }

    @Override
    public String getCredentials() { //相当于密码
        return token;
    }
}
