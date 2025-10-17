package com.dingzk.dingapi.converter;

import com.dingzk.dingapi.model.dto.interfaceinfo.InterfaceInfoRequest;
import com.dingzk.dingapi.model.entity.InterfaceInfo;
import com.dingzk.dingapi.model.entity.User;
import com.dingzk.dingapi.model.vo.InterfaceInfoVo;
import com.dingzk.dingapi.model.vo.UserVo;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class InterfaceInfoConverter {
    /**
     * interfaceInfo 列表转换为 interfaceInfoVo 列表
     * @param interfaceInfoList interfaceInfoList
     * @return interfaceInfoVoList
     */
    public static List<InterfaceInfoVo> convertToInterfaceInfoVoList(List<InterfaceInfo> interfaceInfoList, List<User> creatorList) {
        if (interfaceInfoList.isEmpty()) {
            return Collections.emptyList();
        }
        // 创建者处理
        Map<Long, UserVo> creatorIdVoMap = creatorList.stream()
                .collect(Collectors.toMap(User::getId, UserConverter::convertToUserVo));
        return interfaceInfoList.stream()
                .map(interfaceInfo -> {
                    InterfaceInfoVo interfaceInfoVo = new InterfaceInfoVo();
                    BeanUtils.copyProperties(interfaceInfo, interfaceInfoVo);
                    // 设置创建者
                    interfaceInfoVo.setCreator(creatorIdVoMap.get(interfaceInfo.getCreatorId()));
                    return interfaceInfoVo;
                }).toList();
    }

    /**
     * interfaceInfo 转换为 interfaceInfoVo
     * @param interfaceInfo interfaceInfo
     * @return interfaceInfoVo
     */
    public static InterfaceInfoVo convertToInterfaceInfoVo(InterfaceInfo interfaceInfo, User creator) {
        if (interfaceInfo == null) {
            return null;
        }
        List<InterfaceInfoVo> interfaceInfoVos = convertToInterfaceInfoVoList(List.of(interfaceInfo), List.of(creator));
        if (interfaceInfoVos.isEmpty()) {
            return null;
        }
        return interfaceInfoVos.get(0);
    }

    /**
     * interfaceInfoRequest 转换为 interfaceInfo
     * @param interfaceInfoRequest interfaceInfoRequest
     * @return interfaceInfo
     * @param <T> interfaceInfoRequestDto
     */
    public static <T extends InterfaceInfoRequest> InterfaceInfo convertToInterfaceInfoDo(T interfaceInfoRequest) {
        if (interfaceInfoRequest == null) {
            return null;
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoRequest, interfaceInfo);
        return interfaceInfo;
    }
}