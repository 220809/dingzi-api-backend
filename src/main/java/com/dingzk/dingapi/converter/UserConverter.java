package com.dingzk.dingapi.converter;

import com.dingzk.dingapi.model.dto.user.UserRequest;
import com.dingzk.dingapi.model.entity.User;
import com.dingzk.dingapi.model.vo.UserVo;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;

public final class UserConverter {
    /**
     * user 列表转换为 userVo 列表
     * @param userList userList
     * @return userVoList
     */
    public static List<UserVo> convertToUserVoList(List<User> userList) {
        if (userList.isEmpty()) {
            return Collections.emptyList();
        }
        return userList.stream()
                .map(user -> {
                    UserVo userVo = new UserVo();
                    BeanUtils.copyProperties(user, userVo);
                    return userVo;
                }).toList();
    }

    /**
     * user 转换为 userVo
     * @param user user
     * @return userVo
     */
    public static UserVo convertToUserVo(User user) {
        if (user == null) {
            return null;
        }
        List<UserVo> userVos = convertToUserVoList(List.of(user));
        if (userVos.isEmpty()) {
            return null;
        }
        return userVos.get(0);
    }

    /**
     * userRequest 转换为 user
     * @param userRequest userRequest
     * @return user
     * @param <T> userRequestDto
     */
    public static <T extends UserRequest> User convertToUserDo(T userRequest) {
        if (userRequest == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        return user;
    }
}