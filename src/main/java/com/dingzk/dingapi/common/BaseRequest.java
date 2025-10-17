package com.dingzk.dingapi.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class BaseRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 6466555653504328999L;

    private Long id;
}
