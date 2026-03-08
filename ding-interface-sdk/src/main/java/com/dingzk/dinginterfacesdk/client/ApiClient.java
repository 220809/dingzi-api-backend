package com.dingzk.dinginterfacesdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.dingzk.dinginterfacesdk.utils.SignUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import static com.dingzk.dinginterfacesdk.utils.SignUtils.*;

public class ApiClient {
    private final String accessKey;
    private final String secretKey;

    public ApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    /**
     * 测试指定开发的接口：获取随机数
     * @param body 请求体
     * @return string
     */
    public String getRandomInteger(String body) {
        Gson gson = new Gson();
        HashMap<String, String> bodyMap = gson.fromJson(body, new TypeToken<HashMap<String, String>>() {});
        String value = bodyMap.get("rangeMax");
        if (value == null) {
            throw new RuntimeException("参数不正确");
        }
        Integer rangeMax = null;
        try {
            rangeMax = Integer.valueOf(value);
        } catch (Exception e) {
            throw new RuntimeException("参数格式不正确");
        }
        String nonce = RandomUtil.randomNumbers(6);
        long timestamp = System.currentTimeMillis();
        HttpRequest httpRequest = HttpRequest.get("http://localhost:10010/api/test/randInt?rangeMax=" + rangeMax)
                .header("nonce", nonce)
                .header("timestamp", String.valueOf(timestamp))
                .header("body", body)
                .header("accessKey", accessKey)
                .header("sign", SignUtils.generateSign(nonce, timestamp, body, secretKey));

        try (HttpResponse httpResponse = httpRequest.execute()) {
            if (!httpResponse.isOk()) {
                throw new RuntimeException("接口调用失败");
            }
            return httpResponse.body();
        }
    }
}
