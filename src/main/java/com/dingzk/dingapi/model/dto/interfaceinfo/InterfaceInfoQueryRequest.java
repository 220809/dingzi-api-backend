package com.dingzk.dingapi.model.dto.interfaceinfo;

import com.dingzk.dingapi.common.BasePageRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoQueryRequest extends BasePageRequest implements InterfaceInfoRequest {
    @Serial
    private static final long serialVersionUID = 3454284554170795379L;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 请求方式
     */
    @Pattern(regexp = "(GET|POST)", message = "请输入有效的请求方式：GET, POST")
    private String method;

    /**
     * 创建者id
     */
    @Min(value = 1, message = "不合法的创建者ID")
    private Long creatorId;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    @Min(value = 0, message = "请输入有效的接口状态：0-关闭, 1-开启")
    @Max(value = 1, message = "请输入有效的接口状态：0-关闭, 1-开启")
    private Integer status;

    /**
     * 创建时间-起始
     */
    private Date createBeginTime;

    /**
     * 创建时间-截至
     */
    private Date createEndTime;
}