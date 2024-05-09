package com.jinghai.system.domain.vo;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户实体类
 * </p>
 */
@Data
@Accessors(chain = true)
public class LoginVo implements Serializable {

    private static final long serialVersionUID = 1L;
    //账号
    private String username;
    //头像
    private String avatar;
    //token
    private String token;
    //描述
    private String desc;
}
