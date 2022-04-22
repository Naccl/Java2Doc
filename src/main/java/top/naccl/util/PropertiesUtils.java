package top.naccl.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件工具类
 *
 * @author: Naccl
 * @date: 2022-04-21
 */
@Slf4j
public class PropertiesUtils {
    /**
     * 配置文件
     */
    private static Properties properties;

    /**
     * 加载配置文件
     */
    static {
        properties = new Properties();
        try (InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("app.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("An exception occurred during the loading of app.properties", e);
        }
    }

    /**
     * 获取配置文件中的值
     *
     * @param key 配置文件中的key
     * @return 配置文件中的value
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    public static String getVersion() {
        return properties.getProperty("java2doc.version");
    }
}
