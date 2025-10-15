package com.dingzk.dingapi.model.dto.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserUpdateRequest extends UserRequest {
    @Serial
    private static final long serialVersionUID = 3913931453421876899L;
    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    @Pattern(regexp = "[\\S]{1,20}", message = "用户名应为1-20位非空格字符的组合")
    private String username;

    /**
     * 账号
     */
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]{4,8}$", message = "账号应为4-8位中文、英文、数字或下划线的组合")
    private String userAccount;

    /**
     * 密码
     */
    @Pattern(regexp = "^[A-Za-z0-9]{8,16}$", message = "密码应为8-16位中文、英文或数字的组合")
    private String password;

    /**
     * 用户头像
     */
    @Pattern(regexp = "[a-zA-z]+://[\\S]*", message = "请输入有效的头像地址")
    private String userAvatar;

    /**
     * 性别 0-未知，1-男，2-女
     */
    @Max(value = 2, message = "请选择正确的性别编号:0,1,2")
    @Min(value = 0, message = "请选择正确的性别编号:0,1,2")
    private Integer gender;

    /**
     * 用户角色：0-user / 1-admin
     */
    @Max(value = 1, message = "请输入正确的角色编号:0,1")
    @Min(value = 0, message = "请输入正确的角色编号:0,1")
    private Integer userRole;
}