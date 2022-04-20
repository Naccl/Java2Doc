package top.naccl.engine;

import top.naccl.MainConfig;
import top.naccl.engine.freemarker.FreemarkerTemplateEngine;

/**
 * 模板引擎工厂
 *
 * @author: Naccl
 * @date: 2022-04-19
 */
public class TemplateEngineFactory {
    /**
     * 主配置
     */
    private final MainConfig config;

    public TemplateEngineFactory(MainConfig config) {
        this.config = config;
    }

    /**
     * 工厂方法
     *
     * @return 模板引擎
     */
    public TemplateEngine newInstance() {
        switch (config.getTemplateEngineConfig().getTemplateEngineType()) {
            case FREEMARKER:
                return new FreemarkerTemplateEngine(config);
            default:
                throw new RuntimeException("TemplateEngineType not support");
        }
    }
}
