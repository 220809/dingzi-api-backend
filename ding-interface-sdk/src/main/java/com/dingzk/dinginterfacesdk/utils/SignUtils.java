package com.dingzk.dinginterfacesdk.utils;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public final class SignUtils {
    private static final Gson gson = new Gson();

    public static String generateSign(String nonce, long timestamp, String body, String secretKey) {
        // 参数按照字典序排序
        Map<String, Object> paramMap = gson.fromJson(body, new TypeToken<TreeMap<String, Object>>() {});
        // 拼接生成签名需要的参数
        StringBuilder signBuilder = new StringBuilder();
        signBuilder.append("nonce=").append(nonce)
                .append("&timestamp=").append(timestamp);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            signBuilder.append(String.format("&%s=", entry.getKey())).append(entry.getValue());
        }
        HMac hMac = new HMac(HmacAlgorithm.HmacSHA256, secretKey.getBytes());
        return hMac.digestHex(signBuilder.toString());
    }

    public static boolean verify(String userSign, String serverSign) {
        if (StrUtil.isEmpty(serverSign)) {
            throw new RuntimeException("系统错误");
        }
        return serverSign.equals(userSign);
    }
}
