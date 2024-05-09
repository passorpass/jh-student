package com.jinghai.framework.exception;

import com.jinghai.common.Enum.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.authz.UnauthorizedException;

@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException  {

    // 异常状态码
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param message
     * @param code
     */
    public ServiceException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public ServiceException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
}
