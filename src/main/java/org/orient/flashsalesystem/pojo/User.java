package org.orient.flashsalesystem.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author orient
 * @since 2025-03-24
 */
@Data
@TableName("t_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID, 手机号码
     */
    @ApiModelProperty("用户ID, 手机号码")
    private Long id;

    private String nickname;

    /**
     * MD5(MD5(pass明文 + 固定salt)+salt)
     */
    @ApiModelProperty("MD5(MD5(pass明文 + 固定salt)+salt)")
    private String password;

    private String salt;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String head;

    /**
     * 注册时间
     */
    @ApiModelProperty("注册时间")
    private LocalDateTime redisterDate;

    /**
     * 最后登录时间
     */
    @ApiModelProperty("最后登录时间")
    private LocalDateTime lastLoginDate;

    /**
     * 登录次数
     */
    @ApiModelProperty("登录次数")
    private Integer loginCount;
}
