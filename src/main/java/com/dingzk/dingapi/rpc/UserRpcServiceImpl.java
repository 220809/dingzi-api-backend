package com.dingzk.dingapi.rpc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dingzk.dingapi.common.ErrorCode;
import com.dingzk.dingapi.exception.BusinessException;
import com.dingzk.dingapi.mapper.UserMapper;
import com.dingzk.dingapicommon.entity.User;
import com.dingzk.dingapicommon.rpc.UserRpcService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 提供部分用户服务功能用于远程调用
 */
@DubboService
public class UserRpcServiceImpl implements UserRpcService {

    @Resource
    private UserMapper userMapper;

    @Override
    public String getUserSecretKeyByAccessKey(String accessKey) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccessKey, accessKey)
                .select(User::getSecretKey);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null || user.getSecretKey() == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "用户不存在");
        }
        return user.getSecretKey();
    }

    @Override
    public boolean consumeUserInvokeLimit(long userId) {
        User user = userMapper.selectById(userId);
        if (user.getInvokeLimit() <= 0) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "剩余调用次数不足");
        }
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id", userId)
                .set("invoke_limit", user.getInvokeLimit() - 1);
        int result = userMapper.update(userUpdateWrapper);
        if (result != 1) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return true;
    }
}
