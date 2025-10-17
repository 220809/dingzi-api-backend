package com.dingzk.dingapi.model.dto.user;

import com.dingzk.dingapi.common.BasePageRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends BasePageRequest implements UserRequest {
    @Serial
    private static final long serialVersionUID = 3913931453421876899L;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号
     */
    private String userAccount;

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