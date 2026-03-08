package com.dingzk.dingapi.controller;

import com.dingzk.dingapi.common.BaseResponse;
import com.dingzk.dingapi.common.ErrorCode;
import com.dingzk.dingapi.common.ResUtils;
import com.dingzk.dingapi.exception.BusinessException;
import com.dingzk.dingapi.model.dto.ApiInvokeRequest;
import com.dingzk.dingapi.model.entity.InterfaceInfo;
import com.dingzk.dingapi.service.InterfaceInfoService;
import com.dingzk.dingapi.service.UserService;
import com.dingzk.dingapicommon.entity.User;
import com.dingzk.dinginterfacesdk.client.ApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/interface")
@Tag(name = "Interface", description = "接口调用")
public class InterfaceController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @PostMapping("/invoke")
    public BaseResponse<Object> interfaceInvoke(@RequestBody ApiInvokeRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.BAD_PARAM, "请求参数为空");
        }
        User loginUser = userService.getLoginUser();
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        Long interfaceInfoId = request.getId();
        if (interfaceInfoId == null || interfaceInfoId <= 0) {
            throw new BusinessException(ErrorCode.BAD_PARAM);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(interfaceInfoId);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "接口不存在");
        }
        User user = userService.getById(loginUser.getId());
        // ak、sk
        ApiClient apiClient = new ApiClient(user.getAccessKey(), user.getSecretKey());

        String result = apiClient.getRandomInteger(request.getRequestParam());
        return ResUtils.success(result);
    }

    private Map<String, Object> parseApiRequestParam(String requestParamStr) {
        Gson gson = new Gson();
        JsonObject object = gson.fromJson(requestParamStr, JsonObject.class);
        Map<String, Object> result = new HashMap<>();
        object.asMap().forEach((key, value) -> {
            JsonElement element = object.get(key);
            // 目前仅处理单参数
            if (element instanceof JsonObject obj) {
                String type = obj.get("type").getAsString();
                JsonElement v = obj.get("value");
                Object res = null;
                if ("number".equals(type)) {
                    res = v.getAsLong();
                } else if ("string".equals(type)) {
                    res = v.getAsString();
                }
                result.put(key, res);
            }
        });
        return result;
    }
}
