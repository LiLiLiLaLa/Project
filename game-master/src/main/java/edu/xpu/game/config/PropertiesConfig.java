package edu.xpu.game.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @className PropertiesConfig
 * @description 应用启动加载文件
 * @date 2019-05-31 08:07
 */
@Component
public class PropertiesConfig implements ApplicationListener {
    //保存加载配置参数
    private static Map<String, String> aliPropertiesMap = new HashMap<>();

    /*获取配置参数值*/
    public static String getKey(String key) {
        return aliPropertiesMap.get(key);
    }

    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationReadyEvent) {
            this.init(aliPropertiesMap);
        }
    }


    public void init(Map<String, String> map) {
        // 获得PathMatchingResourcePatternResolver对象
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            //加载resource文件(也可以加载resources)
            Resource resources = resolver.getResource("classpath:config/alipay.properties");
            PropertiesFactoryBean config = new PropertiesFactoryBean();
            config.setLocation(resources);
            config.afterPropertiesSet();
            Properties prop = config.getObject();
            //循环遍历所有得键值对并且存入集合
            assert prop != null;
            for (String key : prop.stringPropertyNames()) {
                map.put(key, (String) prop.get(key));
            }
        } catch (Exception e) {
            throw new RuntimeException("配置文件加载失败");
        }
    }
}
