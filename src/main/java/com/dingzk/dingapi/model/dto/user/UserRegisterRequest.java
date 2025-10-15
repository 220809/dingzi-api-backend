package com.dingzk.dingapi.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRegisterRequest extends UserRequest {
    @Serial
    private static final long serialVersionUID = 3454284554170795379L;
    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]{4,8}$", message = "账号应为4-8位中文、英文、数字或下划线的组合")
    private String userAccount;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]{8,16}$", message = "密码应为8-16位中文、英文或数字的组合")
    private String password;
    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]{8,16}$", message = "确认密码应为8-16位中文、英文或数字的组合")
    private String checkedPassword;
}