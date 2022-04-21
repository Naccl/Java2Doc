package top.naccl;

import top.naccl.engine.DocumentType;
import top.naccl.engine.TemplateEngineConfig;
import top.naccl.engine.TemplateEngineType;
import top.naccl.process.ProcessConfig;
import top.naccl.util.FileUtils;

import java.util.List;

/**
 * 配置工厂
 *
 * @author: Naccl
 * @date: 2022-04-21
 */
public class ConfigFactory {
    /**
     * 默认文档处理器配置
     *
     * @return 文档处理器配置
     */
    public static ProcessConfig defaultProcessConfig() {
        return ProcessConfig.builder()
                //是否显示访问修饰符(字段、方法)
                .displayAccessModifier(true)
                //忽略没有字段和方法的类
                .ignoreClassWithoutFieldAndMethod(false)
                //忽略没有JavaDoc注释的字段
                .ignoreFieldWithoutComment(false)
                //忽略没有JavaDoc注释的方法
                .ignoreMethodWithoutComment(false)
                .build();
    }

    /**
     * 默认模板引擎配置
     *
     * @return 模板引擎配置
     */
    public static TemplateEngineConfig defaultTemplateEngineConfig() {
        return TemplateEngineConfig.builder()
                //文档输出目录
                .fileOutputDir(FileUtils.getCurrentPath())
                //生成文档名称
                .fileName("Java2Doc")
                //生成文档类型
                .documentType(DocumentType.WORD)
                //模板引擎类型
                .templateEngineType(TemplateEngineType.FREEMARKER)
                .build();
    }

    /**
     * 默认主配置
     *
     * @return 主配置
     */
    public static MainConfig defaultMainConfig(List<String> projectPaths) {
        return MainConfig.builder()
                .projectPaths(projectPaths)
                //遍历目录的最大深度(默认Integer.MAX_VALUE)
                .maxDepth(Integer.MAX_VALUE)
                //项目名称
                .projectName("Java2Doc")
                //文档版本
                .version("1.0.0")
                //文档描述
                .description("文档大师必备工具！")
                //解析private的类和成员(默认true)
                .includePrivate(true)
                //忽略解析JavaDoc时的错误(默认true)
                .ignoreJavaDocError(true)
                //模板引擎配置
                .templateEngineConfig(defaultTemplateEngineConfig())
                //文档处理器配置
                .processConfig(defaultProcessConfig())
                .build();
    }
}
