package com.jinghai.framework.shiro.Realm;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jinghai.common.Enum.ResultCodeEnum;
import com.jinghai.common.constant.Constant;
import com.jinghai.common.context.BaseContext;
import com.jinghai.common.properties.SufferProperty;
import com.jinghai.common.util.TokenUtils;
import com.jinghai.framework.exception.ServiceException;
import com.jinghai.framework.shiro.JwtToken;
import com.jinghai.system.domain.entity.JhAdmin;
import com.jinghai.system.service.JhAdminService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import javax.annotation.Resource;
import java.util.*;

import static cn.hutool.poi.excel.sax.AttributeName.s;

/**
 * @Description:自定义Realm 处理登录 权限
 */

@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Resource
    private JhAdminService adminService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //这里进行aop的鉴权认证，由于我们只有管理端，所有权限认证直接略过，可根据后续拓展再进行权限认证
        //TODO

        System.out.println("执行授权逻辑");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<String> authoList = Arrays.asList("user:add", "user:list", "user:del", "user:update");
        info.addStringPermissions(authoList);
        System.out.println("getStringPermissions = " + info.getStringPermissions());

        return info;
    }

    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {

        log.info("进入认证 ===================》");

        //获取token
        String token = (String) authenticationToken.getCredentials();

        try {

            Claims claims = TokenUtils.parseJWT(Constant.TOKEN_SECRET_KEY, token);
            Long adminId = Long.valueOf(claims.get("admin").toString());
            BaseContext.setCurrentId(adminId);

            return new SimpleAuthenticationInfo(token, token, null,
                    getName());

        } catch (Exception ex) {
            //4、不通过，响应401状态
            return null;
        }
    }
}
