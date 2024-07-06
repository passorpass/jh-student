package com.jinghai.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jinghai.common.Enum.ResultCodeEnum;
import com.jinghai.common.constant.Constant;
import com.jinghai.common.util.EncryUtil;
import com.jinghai.common.util.TokenUtils;
import com.jinghai.framework.exception.ServiceException;
import com.jinghai.system.domain.entity.JhUser;
import com.jinghai.system.domain.entity.JhUserRole;
import com.jinghai.system.domain.vo.LoginVo;
import com.jinghai.system.mapper.JhAdminMapper;
import com.jinghai.system.mapper.JhUserRoleMapper;
import com.jinghai.system.service.JhAdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.jinghai.common.properties.SufferProperty.ENCRYSUFFER;

/**
 * @author lenovo
 * @description 针对表【jh_admin(管理员登录表)】的数据库操作Service实现
 * @createDate 2024-05-05 09:30:01
 */
@Service
public class JhAdminServiceImpl extends ServiceImpl<JhAdminMapper, JhUser>
        implements JhAdminService {

    @Resource
    private JhAdminMapper jhAdminMapper;

    @Resource
    private JhUserRoleMapper roleMapper;

    @Override
    public LoginVo login(String username, String password) {
        //查询用户
        JhUser jhUser =
                jhAdminMapper.selectOne(new LambdaQueryWrapper<JhUser>().eq(JhUser::getAdminUsername,
                        username));
        if (ObjectUtil.isEmpty(jhUser)) {
            throw new ServiceException(ResultCodeEnum.FAIL);
        }
        //校验密码
        String newpassWord = ENCRYSUFFER + password;
        if (!EncryUtil.comparePasswords(newpassWord, jhUser.getAdminPassword())) {
            throw new ServiceException(ResultCodeEnum.FAIL);
        }
//        登录成功生成token
//        生成用户token
        Map<String, Object> claims = new HashMap<>();
        claims.put("admin", jhUser.getAdminId());
        String token = TokenUtils.createJWT(
                Constant.TOKEN_SECRET_KEY,
                Constant.TOKEN_EXPIRE_TIME,
                claims);
        JhUserRole jhUserRole = roleMapper.selectOne(new LambdaQueryWrapper<JhUserRole>().eq(JhUserRole::getUserId, jhUser.getAdminId()));
        LoginVo LoginInfo = new LoginVo()
                .setDesc(jhUser.getAdminDescribe())
                .setUsername(jhUser.getAdminUsername())
                .setToken(token)
                .setAvatar(jhUser.getAdminAvatar())
                .setRole(jhUserRole.getAuthoRole())
                .setPermissions(Collections.singleton(jhUserRole.getAuthoPermissions()));
        return LoginInfo;
    }
}




