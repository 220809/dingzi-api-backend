package com.dingzk.dingapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.dingzk.dingapi.annotation.Authority;
import com.dingzk.dingapi.common.BaseResponse;
import com.dingzk.dingapi.common.ErrorCode;
import com.dingzk.dingapi.common.ResUtils;
import com.dingzk.dingapi.converter.UserConverter;
import com.dingzk.dingapi.exception.BusinessException;
import com.dingzk.dingapi.model.dto.user.UserLoginRequest;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Tag(name = "用户管理")
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
    @PostMapping("/current")
    public BaseResponse<UserVo> getLoginUser() {
        User loginUser = userService.getLoginUser();
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        UserVo userVo = UserConverter.convertToUserVo(loginUser);
        return ResUtils.success(userVo);
    }

    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout() {
        User loginUser = userService.getLoginUser();
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
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
    public BaseResponse<UserVo> getUserById(Long id) {
        if (id == null) {
            throw new BusinessException(ErrorCode.BAD_PARAM);
        }
        User user = userService.getById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "用户不存在");
        }
        UserVo userVo = UserConverter.convertToUserVo(user);
        return ResUtils.success(userVo);
    }

    @Operation(summary = "根据id删除用户")
    @Authority(role = 1)
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUserById(Long id) {
        if (id == null) {
            throw new BusinessException(ErrorCode.BAD_PARAM);
        }
        // 不允许删除当前登录用户
        if (userService.getLoginUser().getId().equals(id)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "禁止删除当前登录用户");
        }
        boolean result = userService.removeById(id);
        return ResUtils.success(result);
    }

    @Operation(summary = "查询用户")
    @Authority(role = 1)
    @GetMapping("/list")
    public BaseResponse<List<UserVo>> listUsers() {
        List<User> userList = userService.list();
        List<UserVo> userVos = UserConverter.convertToUserVoList(userList);
        return ResUtils.success(userVos);
    }

    @Operation(summary = "分页查询用户")
    @Parameters({
            @Parameter(name = "page", description = "页数"),
            @Parameter(name = "pageSize", description = "每页条数")
    })
    @Authority(role = 1)
    @GetMapping("/list/page")
    public BaseResponse<Page<UserVo>> pageListUsers(int page, int pageSize) {
        Page<User> userPage = userService.page(Page.of(page, pageSize));
        Page<UserVo> userVoPage = new PageDTO<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        List<UserVo> userVos = UserConverter.convertToUserVoList(userPage.getRecords());
        userVoPage.setRecords(userVos);
        return ResUtils.success(userVoPage);
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
}