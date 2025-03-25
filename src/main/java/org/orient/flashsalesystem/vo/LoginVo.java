package org.orient.flashsalesystem.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.orient.flashsalesystem.validation.IsMobile;

/**
 * 登录参数
 */
@Data
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;
    @NotNull
    @Size(min = 32)
    private String password;
}
