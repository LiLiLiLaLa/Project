package edu.xpu.game.util;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import edu.xpu.game.entity.AlipayBean;
import edu.xpu.game.config.PropertiesConfig;

/**
 * @version 1.0
 * @className AlipayUtil
 * @description 支付工具类
 * @date 2019-05-31 08:11
 */
public class AlipayUtil {
    public static String connect(AlipayBean alipayBean) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(
                PropertiesConfig.getKey("gatewayUrl"),//支付宝网关
                PropertiesConfig.getKey("app_id"),//appid
                PropertiesConfig.getKey("merchant_private_key"),//商户私钥
                "json",
                PropertiesConfig.getKey("charset"),//字符编码格式
                PropertiesConfig.getKey("alipay_public_key"),//支付宝公钥
                PropertiesConfig.getKey("sign_type")//签名方式
        );
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(PropertiesConfig.getKey("return_url"));
        alipayRequest.setNotifyUrl(PropertiesConfig.getKey("notify_url"));
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
        return alipayClient.pageExecute(alipayRequest).getBody();
    }
}
