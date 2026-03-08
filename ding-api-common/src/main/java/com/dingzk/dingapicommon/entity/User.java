package com.dingzk.dingapicommon.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="`user`")
@Data
public class User implements Serializable{
    @Serial
    private static final long serialVersionUID = 1002L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String password;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 性别 0-未知，1-男，2-女
     */
    private Integer gender;

    /**
     * 用户角色：0-user / 1-admin
     */
    private Integer userRole;

    /**
     * 接口调用额度
     */
    private Integer invokeLimit;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除 0-未删除，1-已删除
     */
    @TableLogic
    private Integer deleted;
}