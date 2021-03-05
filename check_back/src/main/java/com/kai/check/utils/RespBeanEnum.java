package com.kai.check.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

//公告返回对象枚举
@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {

    // 通用
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常"),
    FAIL(400,"未知错误"),
    AUTHORITY_NOT(406,"权限不足"),
    // 登录模块
    LOGIN_ERROR(500210,"用户名或密码错误"),
    MOBILE_ERROR(500211,"手机号码格式不正确"),
    BIND_ERROR(500212,"验证码错误请重新输入"),
    USER_NOT(500213,"未登录,请登录"),
    UPDATE_ERROR(500214,"更新密码失败"),

    // 提交模块
    COMMIT_SUCCESS(201,"提交成功"),
    COMMIT_ERROR(501,"提交失败"),
    COMMIT_NOT(502,"打开失败")
    ;
    private final Integer code;
    private final String message;
}
