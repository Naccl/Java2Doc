package top.naccl.engine;

import lombok.Getter;

/**
 * 模板引擎类型
 *
 * @author: Naccl
 * @date: 2022-04-19
 */
public enum TemplateEngineType {
    /**
     * FreeMarker模板引擎
     */
    FREEMARKER("/template/freemarker/", ".ftl");

    /**
     * 模板文件目录
     */
    @Getter
    private final String templateDir;
    /**
     * 模板文件拓展名
     */
    @Getter
    private final String templateExtension;

    TemplateEngineType(String templateDir, String templateExtension) {
        this.templateDir = templateDir;
        this.templateExtension = templateExtension;
    }
}
