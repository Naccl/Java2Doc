package top.naccl.process;

import top.naccl.MainConfig;
import top.naccl.model.PackageModel;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 处理器抽象类
 *
 * @author: Naccl
 * @date: 2022-04-19
 */
public abstract class AbstractProcessor {
    /**
     * 包名和包信息的映射
     */
    protected static Map<String, PackageModel> packageMap = new LinkedHashMap<>();
    /**
     * 主配置
     */
    protected MainConfig config;

    protected AbstractProcessor(MainConfig config) {
        this.config = config;
    }
}
