package org.orient.flashsalesystem.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
    MOBILE_ERROR(500211, "手机号码格式不正确"),
    USER_NOT_FOUND(500213, "手机号码不存在"),
    UPDATE_PASSWORD_ERROR(500214, "更新密码失败"),
    BIND_ERROR(500212, "参数校验异常"),
    // 秒杀模块
    EMPTY_STOCK(500500, "库存不足!"),
    REPEAT_ERROR(500501, "该商品每人限购一件!");

    private final Integer code;
    private final String message;
}
