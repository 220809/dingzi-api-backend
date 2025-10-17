package com.dingzk.dingapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingzk.dingapi.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.dingzk.dingapi.model.entity.InterfaceInfo;
import com.dingzk.dingapi.service.InterfaceInfoService;
import com.dingzk.dingapi.mapper.InterfaceInfoMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ding
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2025-10-17 15:43:26
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public List<InterfaceInfo> listInterfaceInfos(InterfaceInfoQueryRequest queryRequest) {
        QueryWrapper<InterfaceInfo> queryWrapper = interfaceInfoMapper.buildQueryWrapper(queryRequest);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoMapper.selectList(queryWrapper);
        return interfaceInfoList;
    }

    @Override
    public Page<InterfaceInfo> pageListInterfaceInfos(InterfaceInfoQueryRequest queryRequest) {
        QueryWrapper<InterfaceInfo> queryWrapper = interfaceInfoMapper.buildQueryWrapper(queryRequest);
        Page<InterfaceInfo> infoPage =
                interfaceInfoMapper.selectPage(Page.of(queryRequest.getPageNum(), queryRequest.getPageSize()), queryWrapper);
        return infoPage;
    }
}