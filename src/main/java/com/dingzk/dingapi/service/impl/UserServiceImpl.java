package com.dingzk.dingapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingzk.dingapi.common.ErrorCode;
import com.dingzk.dingapi.exception.BusinessException;
import com.dingzk.dingapi.mapper.UserMapper;
import com.dingzk.dingapi.model.entity.User;
import com.dingzk.dingapi.service.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
* @author ding
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-10-15 12:54:52
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper userMapper;

    private static final String PWD_SALT = "dingzi";
    @Override
    public long userRegister(User userDo) {
        String userAccount = userDo.getUserAccount();
        String password = userDo.getPassword();
        synchronized (userAccount.intern()) {
            // 账号不能重复
            QueryWrapper<User> queryWrapper = userMapper.buildQueryWrapper(userDo);
            User user = userMapper.selectOne(queryWrapper);
            if (user != null) {
                throw new BusinessException(ErrorCode.BAD_PARAM, "账号重复");
            }
        }
        User newUser = new User();
        newUser.setUsername(userAccount);
        newUser.setUserAccount(userAccount);
        // 加密
        newUser.setPassword(encryptPassword(password));
        userMapper.insert(newUser);
        return newUser.getId();
    }

    @Override
    public long userLogin(User userDo) {
        userDo.setPassword(encryptPassword(userDo.getPassword()));
        QueryWrapper<User> queryWrapper = userMapper.buildQueryWrapper(userDo);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.BAD_PARAM, "用户名或密码错误");
        }
        saveUserLoginState(user);
        return user.getId();
    }

    private String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex((password + PWD_SALT).getBytes());
    }

    @Override
    public boolean updateUser(User user) {
        if (user == null) {
            throw new BusinessException(ErrorCode.BAD_PARAM);
        }
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(encryptPassword(user.getPassword()));
        }
        int result = userMapper.updateById(user);
        if (result <= 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "用户不存在");
        }
        return true;
    }
}