package com.jinghai.framework.exception;

import com.jinghai.common.Enum.ResultCodeEnum;
import com.jinghai.common.util.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class) // 异常处理器
    // 调用前面统一返回结果类
    public Result error(@NotNull RuntimeException e) {
        e.printStackTrace();
        return Result.fail(null);
    }

    // 自定义异常处理
    @ExceptionHandler(ServiceException.class)
    public Result error(@NotNull ServiceException exception) {
        return Result.build(null, exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity handleUnauthorizedException(AuthorizationException ex) {
        Result<Object> result = Result.ThrowException(ResultCodeEnum.PERMISSION);
        return ResponseEntity.status(ResultCodeEnum.PERMISSION.getCode()).body(result); // 使用自定义消息和状态码
    }
}
