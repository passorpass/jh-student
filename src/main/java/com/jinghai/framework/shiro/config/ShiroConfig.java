package com.jinghai.framework.shiro.config;


import com.jinghai.framework.shiro.Realm.LoginRealm;
import com.jinghai.framework.shiro.Realm.UserRealm;

import com.jinghai.framework.shiro.filter.JwtFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:shiro配置类
 */
@Configuration
public class ShiroConfig {


    /*
     * 解决spring aop和注解配置一起使用的bug。如果您在使用shiro注解配置的同时，引入了spring
     * aop的starter，会有一个奇怪的问题，导致shiro注解的请求，不能被映射
     */
//    @Bean
//    public static DefaultAdvisorAutoProxyCreator creator() {
//        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
//        creator.setProxyTargetClass(true);
//        return creator;
//    }

    /**
     * Enable Shiro AOP annotation support. --<1>
     *
     * @param securityManager Security Manager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //添加Shiro内置过滤器
        Map<String, String> filterMap = new LinkedHashMap<String, String>();

        filterMap.put("/admin/login", "anon");
        filterMap.put("/admin/logout", "anon");
        Map<String, Filter> filterMaps = new LinkedHashMap<String, Filter>();
        // 添加自己的自定义拦截器并且取名为jwt
        filterMaps.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMaps);
        // 设置需要认证的路径，以及相应的角色或权限
        filterMap.put("/**", "jwt");
        //配置拦截链到过滤器工厂
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        //返回实例
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    /**
     * 安全管理器
     * 它是Shiro框架的核心，典型的Facade模式，Shiro通过SecurityManager来管理内部组件实例，
     * 并通过它来提供安全管理的各种服务
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(UserRealm userRealm,
                                                     LoginRealm loginRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义Realm验证，这个Realm是最终用于完成我们的认证和授权操作的具体对象
        //关联realm
        List<Realm> realms = new ArrayList<>();
        realms.add(userRealm);
        realms.add(loginRealm);
        securityManager.setRealms(realms);
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }


    /**
     * Realm for login --<3>
     *
     * @param matcher password matcher
     * @return PasswordRealm
     */


    /**
     * 创建Realm
     */
    @Bean
    public UserRealm getUserRealm() {
        return new UserRealm();
    }

    @Bean
    public LoginRealm getLoginRealm(@Qualifier(value = "hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        LoginRealm loginRealm = new LoginRealm("LOGIN");
        loginRealm.setCredentialsMatcher(matcher);
        return loginRealm;
    }

    /**
     * Use for login password matcher --<2>
     *
     * @return HashedCredentialsMatcher
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // set name of hash
        matcher.setHashAlgorithmName("SHA-256");
        // Storage format is hexadecimal
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }


}

