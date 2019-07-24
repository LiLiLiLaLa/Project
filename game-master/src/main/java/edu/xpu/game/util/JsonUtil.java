package edu.xpu.game.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @version 1.0
 * @className JsonUtil
 * @description
 * @date 2019-06-04 14:24
 */
public class JsonUtil {
    public static String toJson(Object object) {
        return JSONObject.toJSONString(object, true);
    }
}
