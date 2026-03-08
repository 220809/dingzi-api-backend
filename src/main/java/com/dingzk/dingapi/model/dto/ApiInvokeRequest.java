package com.dingzk.dingapi.model.dto;

import com.dingzk.dingapi.common.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApiInvokeRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = 6375446824084883667L;

    private String requestParam;
}
