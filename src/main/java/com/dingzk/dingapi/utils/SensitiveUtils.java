package com.dingzk.dingapi.utils;

import com.dingzk.dingapi.enums.SensitiveType;
import org.apache.commons.lang3.StringUtils;

public final class SensitiveUtils {
    public static String desensitize(String data, SensitiveType type) {
        if (StringUtils.isBlank(data)) {
            return "";
        }

        return switch (type) {
            case PASSWORD -> "********";
            case MOBILE -> data.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3");
        };
    }
}
