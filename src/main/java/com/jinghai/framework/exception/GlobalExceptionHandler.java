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

    // 自定义异常处理
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity error(@NotNull ServiceException exception) {
        Result<Object> build = Result.build(null, exception.getCode(), exception.getMessage());
        return ResponseEntity.status(exception.getCode()).body(build);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity handleUnauthorizedException(AuthorizationException ex) {
        Result<Object> result = Result.ThrowException(ResultCodeEnum.PERMISSION);
        return ResponseEntity.status(ResultCodeEnum.PERMISSION.getCode()).body(result); // 使用自定义消息和状态码
    }
}
