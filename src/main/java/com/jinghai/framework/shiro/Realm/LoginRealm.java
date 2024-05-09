package com.jinghai.framework.shiro.Realm;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jinghai.common.constant.Constant;
import com.jinghai.common.util.TokenUtils;
import com.jinghai.framework.shiro.service.impl.LoginDataAutoToken;
import com.jinghai.system.domain.entity.JhAdmin;
import com.jinghai.system.service.JhAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginRealm extends AuthorizingRealm {

    public LoginRealm(String name){
        setName(name);
    }


    // 获取user相关信息的service类
    @Resource
    private JhAdminService adminService;

    // supports方法必须重写，这是shiro处理流程中的一部分，他会通过此方法判断realm是否匹配的正确
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof LoginDataAutoToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        LoginDataAutoToken token = (LoginDataAutoToken) auth;
        log.info(token.getUsername() + " password auth start...");
        JhAdmin jhAdmin =
                adminService.getOne(new LambdaQueryWrapper<JhAdmin>().eq(JhAdmin::getAdminUsername
        ,token.getUsername()));
        if (jhAdmin == null) throw new UnknownAccountException();
        Map<String, Object> claims = new HashMap<>();
        claims.put("admin", jhAdmin.getAdminId());
        String authtoken = TokenUtils.createJWT(
                Constant.TOKEN_SECRET_KEY,
                Constant.TOKEN_EXPIRE_TIME,
                claims);
        System.out.println(authtoken);
        // save username and role to Attribute
        return new SimpleAuthenticationInfo(authtoken, token, super.getName());
    }
}
