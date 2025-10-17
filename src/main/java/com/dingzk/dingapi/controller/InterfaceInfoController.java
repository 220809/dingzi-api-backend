package com.dingzk.dingapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.dingzk.dingapi.annotation.Authority;
import com.dingzk.dingapi.common.*;
import com.dingzk.dingapi.converter.InterfaceInfoConverter;
import com.dingzk.dingapi.exception.BusinessException;
import com.dingzk.dingapi.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.dingzk.dingapi.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.dingzk.dingapi.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.dingzk.dingapi.model.entity.InterfaceInfo;
import com.dingzk.dingapi.model.entity.User;
import com.dingzk.dingapi.model.vo.InterfaceInfoVo;
import com.dingzk.dingapi.service.InterfaceInfoService;
import com.dingzk.dingapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.apache.commons.lang3.ObjectUtils;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/interfaceInfo")
@Tag(name = "接口管理")
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Operation(summary = "添加接口")
    @Authority(role = 1)
    @PostMapping("/add")
    public BaseResponse<Long> interfaceInfoRegister(@RequestBody @Valid InterfaceInfoAddRequest addRequest) {
        if (addRequest == null) {
            throw new BusinessException(ErrorCode.BAD_PARAM, "请求参数为空");
        }

        InterfaceInfo interfaceInfoDo = InterfaceInfoConverter.convertToInterfaceInfoDo(addRequest);
        boolean result = interfaceInfoService.save(interfaceInfoDo);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResUtils.success(interfaceInfoDo.getId());
    }

    @Operation(summary = "根据id获取接口")
    @Parameters({
            @Parameter(name = "id", required = true)
    })
    @GetMapping("/get")
    public BaseResponse<InterfaceInfoVo> getInterfaceInfoById(@ParameterObject BaseRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.BAD_PARAM);
        }
        // 获取接口信息
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(request.getId());

        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "接口不存在");
        }
        // 获取创建者
        User user = userService.getById(interfaceInfo.getCreatorId());
        // 转换 vo
        InterfaceInfoVo interfaceInfoVo = InterfaceInfoConverter.convertToInterfaceInfoVo(interfaceInfo, user);
        return ResUtils.success(interfaceInfoVo);
    }

    @Operation(summary = "根据id删除接口")
    @Authority(role = 1)
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfoById(@ParameterObject BaseRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.BAD_PARAM);
        }

        boolean result = interfaceInfoService.removeById(request.getId());
        return ResUtils.success(result);
    }

    // todo: 添加过滤条件
    @Operation(summary = "查询接口")
    @Parameters({
            @Parameter(name = "name", description = "接口名称"),
            @Parameter(name = "method", description = "请求方法"),
            @Parameter(name = "creatorId", description = "创建者ID"),
            @Parameter(name = "status", description = "接口状态"),
            @Parameter(name = "createBeginTime", description = "起始创建时间"),
            @Parameter(name = "createEndTime", description = "截止创建时间"),
            @Parameter(name = "pageNum", description = "当前页数", hidden = true),
            @Parameter(name = "pageSize", description = "每页条数", hidden = true)
    })
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfoVo>> listInterfaceInfos(@ParameterObject InterfaceInfoQueryRequest queryRequest) {
        // todo 通过接口调用时日期解析问题
        Date beginTime = queryRequest.getCreateBeginTime();
        Date endTime = queryRequest.getCreateEndTime();
        if (ObjectUtils.allNotNull(beginTime, endTime) && beginTime.before(endTime)) {
            throw new BusinessException(ErrorCode.BAD_PARAM, "起始时间不能超过截止时间");
        }
        // 获取接口信息列表
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.listInterfaceInfos(queryRequest);
        // 未找到接口直接返回空列表
        if (interfaceInfoList.isEmpty()) {
            return ResUtils.success(Collections.emptyList());
        }
        // 统一获取接口创建者列表
        Set<Long> creatorIds = interfaceInfoList.stream().map(InterfaceInfo::getCreatorId).collect(Collectors.toSet());
        List<User> creatorList = userService.listByIds(creatorIds);
        // 转换 vo
        List<InterfaceInfoVo> interfaceInfoVos = InterfaceInfoConverter.convertToInterfaceInfoVoList(interfaceInfoList, creatorList);
        return ResUtils.success(interfaceInfoVos);
    }

    // todo: 添加过滤条件
    @Operation(summary = "分页查询接口")
    @Parameters({
            @Parameter(name = "name", description = "接口名称"),
            @Parameter(name = "method", description = "请求方法"),
            @Parameter(name = "creatorId", description = "创建者ID"),
            @Parameter(name = "status", description = "接口状态"),
            @Parameter(name = "createBeginTime", description = "起始创建时间"),
            @Parameter(name = "createEndTime", description = "截止创建时间"),
            @Parameter(name = "pageNum", description = "当前页数"),
            @Parameter(name = "pageSize", description = "每页条数")
    })
    @GetMapping("/list/page")
    public BaseResponse<PageVo<InterfaceInfoVo>> pageListInterfaceInfos(@ParameterObject InterfaceInfoQueryRequest queryRequest) {
        Date beginTime = queryRequest.getCreateBeginTime();
        Date endTime = queryRequest.getCreateEndTime();
        if (ObjectUtils.allNotNull(beginTime, endTime) && beginTime.before(endTime)) {
            throw new BusinessException(ErrorCode.BAD_PARAM, "起始时间不能超过截止时间");
        }
        // 获取接口信息列表
        Page<InterfaceInfo> interfaceInfoPage =
                interfaceInfoService.pageListInterfaceInfos(queryRequest);
        Page<InterfaceInfoVo> interfaceInfoVoPage =
                new PageDTO<>(interfaceInfoPage.getCurrent(), interfaceInfoPage.getSize(), interfaceInfoPage.getTotal());
        List<InterfaceInfo> interfaceInfoList = interfaceInfoPage.getRecords();
        // 数据为空直接返回空列表
        if (interfaceInfoList.isEmpty()) {
            return ResUtils.success(PageVo.fromPage(interfaceInfoVoPage));
        }
        // 统一获取接口创建者列表
        Set<Long> creatorIds = interfaceInfoList.stream().map(InterfaceInfo::getCreatorId).collect(Collectors.toSet());
        List<User> creatorList = userService.listByIds(creatorIds);
        // 转换 vo
        List<InterfaceInfoVo> interfaceInfoVos =
                InterfaceInfoConverter.convertToInterfaceInfoVoList(interfaceInfoList, creatorList);
        interfaceInfoVoPage.setRecords(interfaceInfoVos);
        return ResUtils.success(PageVo.fromPage(interfaceInfoVoPage));
    }

    @Operation(summary = "修改接口")
    @Authority(role = 1)
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody @Valid InterfaceInfoUpdateRequest updateRequest) {
        if (updateRequest == null) {
            throw new BusinessException(ErrorCode.BAD_PARAM);
        }
        InterfaceInfo interfaceInfoDo = InterfaceInfoConverter.convertToInterfaceInfoDo(updateRequest);
        boolean result = interfaceInfoService.updateById(interfaceInfoDo);
        return ResUtils.success(result);
    }
}