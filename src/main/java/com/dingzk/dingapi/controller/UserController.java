package com.dingzk.dingapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.dingzk.dingapi.annotation.Authority;
import com.dingzk.dingapi.common.*;
import com.dingzk.dingapi.converter.UserConverter;
import com.dingzk.dingapi.exception.BusinessException;
import com.dingzk.dingapi.model.dto.user.UserLoginRequest;
import com.dingzk.dingapi.model.dto.user.UserQueryRequest;
import com.dingzk.dingapi.model.dto.user.UserRegisterRequest;
import com.dingzk.dingapi.model.dto.user.UserUpdateRequest;
import com.dingzk.dingapi.model.entity.User;
import com.dingzk.dingapi.model.vo.UserVo;
import com.dingzk.dingapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import com.dingzk.dinginterfacesdk.client.TestClient;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "用户管理")
public class UserController {

    @Resource
    private UserService userService;

    @Operation(summary = "注册用户")
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody @Valid UserRegisterRequest registerRequest) {
        if (registerRequest == null) {
            throw new BusinessException(ErrorCode.BAD_PARAM, "请求参数为空");
        }
        String password = registerRequest.getPassword();
        String checkedPassword = registerRequest.getCheckedPassword();
        if (!Objects.equals(password, checkedPassword)) {
            throw new BusinessException(ErrorCode.BAD_PARAM, "密码与确认密码不一致");
        }
        User userDo = UserConverter.convertToUserDo(registerRequest);
        long result = userService.userRegister(userDo);
        return ResUtils.success(result);
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public BaseResponse<Long> userLogin(@RequestBody @Valid UserLoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new BusinessException(ErrorCode.BAD_PARAM, "请求参数为空");
        }
        User userDo = UserConverter.convertToUserDo(loginRequest);
        long result = userService.userLogin(userDo);
        return ResUtils.success(result);
    }

    @Operation(summary = "获取登录用户")
    @Authority
    @GetMapping("/current")
    public BaseResponse<UserVo> getLoginUser() {
        User loginUser = userService.getLoginUser();
        UserVo userVo = UserConverter.convertToUserVo(loginUser);
        return ResUtils.success(userVo);
    }

    @Operation(summary = "退出登录")
    @Authority
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout() {
        userService.saveUserLoginState(null);
        return ResUtils.success(true);
    }

    // region 管理员接口
    @Operation(summary = "根据id获取用户")
    @Parameters({
            @Parameter(name = "id", required = true)
    })
    @Authority(role = 1)
    @GetMapping("/get")
    public BaseResponse<UserVo> getUserById(@ParameterObject BaseRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.BAD_PARAM);
        }
        User user = userService.getById(request.getId());
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "用户不存在");
        }
        UserVo userVo = UserConverter.convertToUserVo(user);
        return ResUtils.success(userVo);
    }

    @Operation(summary = "根据id删除用户")
    @Authority(role = 1)
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUserById(@ParameterObject BaseRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.BAD_PARAM);
        }
        Long id = request.getId();
        // 不允许删除当前登录用户
        if (userService.getLoginUser().getId().equals(id)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "禁止删除当前登录用户");
        }
        boolean result = userService.removeById(id);
        return ResUtils.success(result);
    }

    @Operation(summary = "查询用户")
    @Parameters({
            @Parameter(name = "username", description = "用户名"),
            @Parameter(name = "userAccount", description = "用户名"),
            @Parameter(name = "pageNum", description = "当前页数", hidden = true),
            @Parameter(name = "pageSize", description = "每页条数", hidden = true)
    })
    @Authority(role = 1)
    @GetMapping("/list")
    public BaseResponse<List<UserVo>> listUsers(@ParameterObject UserQueryRequest queryRequest) {
        User userDo = UserConverter.convertToUserDo(queryRequest);
        List<User> userList = userService.listUsers(userDo);
        // 数据为空直接返回
        if (userList.isEmpty()) {
            return ResUtils.success(Collections.emptyList());
        }
        List<UserVo> userVos = UserConverter.convertToUserVoList(userList);
        return ResUtils.success(userVos);
    }

    @Operation(summary = "分页查询用户")
    @Parameters({
            @Parameter(name = "username", description = "用户名"),
            @Parameter(name = "userAccount", description = "用户名"),
            @Parameter(name = "pageNum", description = "当前页数"),
            @Parameter(name = "pageSize", description = "每页条数")
    })
    @Authority(role = 1)
    @GetMapping("/list/page")
    public BaseResponse<PageVo<UserVo>> pageListUsers(@ParameterObject UserQueryRequest queryRequest) {
        User userDo = UserConverter.convertToUserDo(queryRequest);
        Page<User> userPage = userService.pageListUsers(userDo, queryRequest.getPageNum(), queryRequest.getPageSize());
        // 转换 pageVo
        Page<UserVo> userVoPage = new PageDTO<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        // 数据为空直接返回
        if (userPage.getRecords().isEmpty()) {
            return ResUtils.success(PageVo.fromPage(userVoPage));
        }
        List<UserVo> userVos = UserConverter.convertToUserVoList(userPage.getRecords());
        userVoPage.setRecords(userVos);
        return ResUtils.success(PageVo.fromPage(userVoPage));
    }

    @Operation(summary = "修改用户")
    @Authority(role = 1)
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody @Valid UserUpdateRequest updateRequest) {
        if (updateRequest == null) {
            throw new BusinessException(ErrorCode.BAD_PARAM);
        }
        User userDo = UserConverter.convertToUserDo(updateRequest);
        boolean result = userService.updateUser(userDo);
        return ResUtils.success(result);
    }

    // endregion

    @Operation(summary = "重新生成apiKeys")
    @Authority
    @PostMapping("/apiKeys/regen")
    public BaseResponse<Boolean> regenUserApiKeys() {
        User loginUser = userService.getLoginUser();
        boolean result = userService.regenUserApiKeys(loginUser);
        return ResUtils.success(result);
    }


    @Resource
    private TestClient testClient;
    @PostMapping("/testApi")
    public BaseResponse<String> testApi() {
        String result = testClient.getRandomInteger(1000);
        return ResUtils.success(result);
    }
}