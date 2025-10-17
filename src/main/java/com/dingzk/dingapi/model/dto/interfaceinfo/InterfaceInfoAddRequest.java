package com.dingzk.dingapi.model.dto.interfaceinfo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;

@Data
public class InterfaceInfoAddRequest implements InterfaceInfoRequest {
    @Serial
    private static final long serialVersionUID = 7921446629762109897L;

    /**
     * 接口名称
     */
    @NotBlank(message = "接口名称不能为空")
    @Pattern(regexp = "^[\\S]{1,50}$", message = "接口名称应为1-50位非空字符的组合")
    private String name;

    /**
     * 接口描述
     */
    @Pattern(regexp = "^[\\S]{0,150}$", message = "接口描述应为150位以内非空字符的组合")
    private String description;

    /**
     * 接口请求地址
     */
    @NotBlank(message = "接口请求地址不能为空")
    @Pattern(regexp = "[a-zA-z]+://[\\S]*", message = "请输入有效的请求地址")
    private String url;

    /**
     * 请求方式
     */
    @Pattern(regexp = "(GET|POST)", message = "请输入有效的请求方式：GET, POST")
    private String method;

    /**
     * 请求头
     */
    private String reqHeader;

    /**
     * 响应头
     */
    private String respHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    @Min(value = 0, message = "请输入有效的接口状态：0-关闭, 1-开启")
    @Max(value = 1, message = "请输入有效的接口状态：0-关闭, 1-开启")
    private Integer status;
}