package top.naccl.engine;

import org.apache.commons.lang3.StringUtils;
import top.naccl.MainConfig;
import top.naccl.util.FileUtils;

import java.io.File;

/**
 * 模板引擎抽象类
 *
 * @author: Naccl
 * @date: 2022-04-19
 */
public abstract class AbstractTemplateEngine implements TemplateEngine {
    /**
     * 主配置
     */
    protected final MainConfig config;

    public AbstractTemplateEngine(MainConfig config) {
        this.config = config;
    }

    /**
     * 根据配置获取模板文件名
     *
     * @return 模板文件名
     */
    protected String getTemplateFileName() {
        String customTemplatePath = config.getTemplateEngineConfig().getCustomTemplatePath();
        if (StringUtils.isNotBlank(customTemplatePath) && FileUtils.isFileExists(customTemplatePath)) {
            //使用自定义模板
            return new File(customTemplatePath).getName();
        }
        //使用自带默认模板
        return config.getTemplateEngineConfig().getDocumentType().getTemplateFileName() + config.getTemplateEngineConfig().getTemplateEngineType().getTemplateExtension();
    }

    /**
     * 根据配置获取输出文件对象
     *
     * @return 输出文件对象
     */
    protected File getOutputFile() {
        String outputPath = config.getTemplateEngineConfig().getFileOutputDir();
        String outputFile = config.getTemplateEngineConfig().getFileName() + config.getTemplateEngineConfig().getDocumentType().getGenerateFileExtension();
        return FileUtils.touchFile(outputPath, outputFile);
    }
}
