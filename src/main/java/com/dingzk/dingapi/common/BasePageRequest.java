package com.dingzk.dingapi.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class BasePageRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 5829892760338707294L;

    private long pageNum = 1;
    private long pageSize = 10;
}
