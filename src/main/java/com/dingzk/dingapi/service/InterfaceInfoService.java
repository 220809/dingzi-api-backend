package com.dingzk.dingapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingzk.dingapi.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.dingzk.dingapi.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ding
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2025-10-17 15:43:26
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 条件查询接口信息
     * @param queryRequest 查询条件
     * @return 接口列表
     * @apiNote 是否需要提取出新的包装类传递查询参数？
     */
    List<InterfaceInfo> listInterfaceInfos(InterfaceInfoQueryRequest queryRequest);

    /**
     * 条件分页查询接口信息
     * @param queryRequest 查询条件
     * @return 接口列表
     * @apiNote 是否需要提取出新的包装类传递查询参数？
     */
    Page<InterfaceInfo> pageListInterfaceInfos(InterfaceInfoQueryRequest queryRequest);
}
