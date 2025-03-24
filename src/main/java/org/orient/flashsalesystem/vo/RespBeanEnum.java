package org.orient.flashsalesystem.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    // common
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务端异常"),
    // 登录模块
    LOGIN_ERROR(500210, "用户名或密码错误"),
    MOBILE_ERROR(500211, "手机号码格式不正确");

    private final Integer code;
    private final String message;
}
