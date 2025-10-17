package com.dingzk.dingapi.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dingzk.dingapi.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.dingzk.dingapi.model.entity.InterfaceInfo;
import org.apache.commons.lang3.StringUtils;

/**
* @author ding
* @description 针对表【interface_info(接口信息)】的数据库操作Mapper
* @createDate 2025-10-17 15:43:26
* @Entity com.dingzk.dingapi.model.entity.InterfaceInfo
*/
public interface InterfaceInfoMapper extends BaseMapper<InterfaceInfo> {

    default QueryWrapper<InterfaceInfo> buildQueryWrapper(InterfaceInfoQueryRequest queryRequest) {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(queryRequest.getName())) {
            queryWrapper.like("name", queryRequest.getName());
        }
        if (StringUtils.isNotBlank(queryRequest.getMethod())) {
            queryWrapper.eq("method", queryRequest.getMethod());
        }
        if (queryRequest.getCreatorId() != null) {
            queryWrapper.eq("creator_id", queryRequest.getCreatorId());
        }
        if (queryRequest.getStatus() != null) {
            queryWrapper.eq("status", queryRequest.getStatus());
        }
        if (queryRequest.getCreateBeginTime() != null) {
            queryWrapper.ge("create_time", queryRequest.getCreateBeginTime());
        }
        if (queryRequest.getCreateEndTime() != null) {
            queryWrapper.le("create_time", queryRequest.getCreateEndTime());
        }
        return queryWrapper;
    }
}