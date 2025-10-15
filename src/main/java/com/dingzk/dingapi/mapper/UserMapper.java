package com.dingzk.dingapi.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dingzk.dingapi.model.entity.User;
import org.apache.commons.lang3.StringUtils;

/**
* @author ding
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2025-10-15 12:54:52
* @Entity com.dingzk.dingapi.model.entity.User
*/
public interface UserMapper extends BaseMapper<User> {

    default QueryWrapper<User> buildQueryWrapper(User userDo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (userDo.getId() != null) {
            queryWrapper.eq("id", userDo.getId());
        }
        if (StringUtils.isNotBlank(userDo.getUsername())) {
             queryWrapper.eq("username", userDo.getUsername());
        }
        if (StringUtils.isNotBlank(userDo.getUserAccount())) {
             queryWrapper.eq("user_account", userDo.getUserAccount());
        }
        if (StringUtils.isNotBlank(userDo.getPassword())) {
             queryWrapper.eq("password", userDo.getPassword());
        }
        if (userDo.getGender() != null) {
            queryWrapper.eq("gender", userDo.getGender());
        }
        if (userDo.getUserRole() != null) {
            queryWrapper.eq("user_role", userDo.getUserRole());
        }
        return queryWrapper;
    }
}