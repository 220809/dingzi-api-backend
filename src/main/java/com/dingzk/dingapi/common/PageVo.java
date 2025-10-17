package com.dingzk.dingapi.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class PageVo<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 369248175673443805L;

    /**
     * 查询数据列表
     */
    private List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    private long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;

    private long pages = 1;

    public static <T> PageVo<T> fromPage(Page<T> page) {
        PageVo<T> pageVo = new PageVo<>();
        pageVo.records = page.getRecords();
        pageVo.size = page.getSize();
        pageVo.total = page.getTotal();
        pageVo.current = page.getCurrent();
        pageVo.pages = (long) Math.ceil((double) page.getTotal() / page.getSize());
        return pageVo;
    }
}
