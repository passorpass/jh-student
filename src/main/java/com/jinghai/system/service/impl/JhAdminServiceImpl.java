package com.jinghai.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jinghai.common.constant.Constant;
import com.jinghai.common.util.EncryUtil;
import com.jinghai.common.util.Result;
import com.jinghai.common.util.TokenUtils;
import com.jinghai.system.domain.entity.JhAdmin;
import com.jinghai.system.domain.vo.LoginVo;
import com.jinghai.system.mapper.JhAdminMapper;
import com.jinghai.system.service.JhAdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.jinghai.common.properties.SufferProperty.ENCRYSUFFER;

/**
* @author lenovo
* @description 针对表【jh_admin(管理员登录表)】的数据库操作Service实现
* @createDate 2024-05-05 09:30:01
*/
@Service
public class JhAdminServiceImpl extends ServiceImpl<JhAdminMapper, JhAdmin>
    implements JhAdminService {

    @Resource
    private JhAdminMapper jhAdminMapper;
    @Override
    public Result login(String username, String password) {
        //获取参数
        String passWord = password;
        String userName = username;
        //查询用户
        JhAdmin jhAdmin =
                jhAdminMapper.selectOne(new LambdaQueryWrapper<JhAdmin>().eq(JhAdmin::getAdminUsername,
                userName));
        if(ObjectUtil.isEmpty(jhAdmin)){
            return Result.fail("用户不存在");
        }
        //校验密码
        String newpassWord =ENCRYSUFFER + passWord;
        if(!EncryUtil.comparePasswords(newpassWord,jhAdmin.getAdminPassword())){
            return Result.fail("密码错误");
        }
//        登录成功生成token
//        生成用户token
        Map<String, Object> claims = new HashMap<>();
        claims.put("admin", jhAdmin.getAdminId());
        String token = TokenUtils.createJWT(
                Constant.TOKEN_SECRET_KEY,
                Constant.TOKEN_EXPIRE_TIME,
                claims);

        LoginVo tokenOf =
                new LoginVo().setDesc(jhAdmin.getAdminDescribe()).setUsername(jhAdmin.getAdminUsername()).setToken(token).setAvatar(jhAdmin.getAdminAvatar());
        return Result.ok(tokenOf);
    }
}




