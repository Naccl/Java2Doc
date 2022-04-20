package top.naccl.engine.freemarker;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import top.naccl.MainConfig;
import top.naccl.engine.AbstractTemplateEngine;
import top.naccl.model.ProjectModel;
import top.naccl.util.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Freemarker模板引擎
 *
 * @author: Naccl
 * @date: 2022-04-19
 */
@Slf4j
public class FreemarkerTemplateEngine extends AbstractTemplateEngine {
    /**
     * Freemarker配置
     */
    private final Configuration freemarkerConfig;

    /**
     * 初始化Freemarker模板引擎配置
     *
     * @param config 主配置
     */
    public FreemarkerTemplateEngine(MainConfig config) {
        super(config);
        freemarkerConfig = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        String customTemplatePath = config.getTemplateEngineConfig().getCustomTemplatePath();
        if (StringUtils.isNotBlank(customTemplatePath) && FileUtils.isFileExists(customTemplatePath)) {
            //使用自定义模板
            File parentPath = new File(customTemplatePath).getParentFile();
            try {
                //设置模板加载路径
                freemarkerConfig.setDirectoryForTemplateLoading(parentPath);
            } catch (IOException e) {
                log.error("An exception occurred during custom Freemarker template engine configuration:", e);
            }
        } else {
            //使用自带模板
            freemarkerConfig.setTemplateLoader(new ClassTemplateLoader(this.getClass(), config.getTemplateEngineConfig().getTemplateEngineType().getTemplateDir()));
        }

        freemarkerConfig.setDefaultEncoding("UTF-8");
        freemarkerConfig.setLocale(Locale.SIMPLIFIED_CHINESE);
    }

    /**
     * 生成文档
     *
     * @param model 模板数据
     * @throws IOException IO异常直接向上抛出
     */
    @Override
    public void produce(ProjectModel model) throws IOException {
        Template template = freemarkerConfig.getTemplate(getTemplateFileName());
        File file = getOutputFile();

        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            template.process(model, out);
        } catch (TemplateException e) {
            log.error("An exception occurred during Freemarker template engine processing:", e);
        }
    }
}
