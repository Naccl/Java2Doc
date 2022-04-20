package top.naccl;

import top.naccl.engine.DocumentType;
import top.naccl.engine.TemplateEngineConfig;
import top.naccl.engine.TemplateEngineType;
import top.naccl.execute.DocumentExecute;
import top.naccl.process.ProcessConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Naccl
 * @date: 2022-04-18
 */
public class Main {

    public static void main(String[] args) {
        new DocumentExecute(miniConfig()).execute();
    }

    /**
     * 基础配置
     *
     * @return MainConfig
     */
    @SuppressWarnings("unchecked all")
    private static MainConfig miniConfig() {
        List<String> projectPaths = new ArrayList<>();

        //==============================需要修改的基础配置==============================
        //待生成的项目根路径列表
        projectPaths.add("/Users/naccl/work/idea-project/Java2Doc");
        //项目名称
        String projectName = "Java2Doc";
        //文档版本
        String version = "1.0.0";
        //文档描述
        String description = "文档大师必备工具！";
        //文档输出目录
        String fileOutputDir = "/Users/naccl/Desktop";
        //生成文档名称
        String fileName = "Java2Doc";
        //生成文档类型 DocumentType.WORD、DocumentType.HTML、DocumentType.MARKDOWN
        DocumentType documentType = DocumentType.WORD;
        //===========================================================================


        ProcessConfig processConfig = ProcessConfig.builder()
                //是否显示访问修饰符(字段、方法)
                .displayAccessModifier(true)
                //忽略没有字段和方法的类
                .ignoreClassWithoutFieldAndMethod(false)
                //忽略没有JavaDoc注释的字段
                .ignoreFieldWithoutComment(false)
                //忽略没有JavaDoc注释的方法
                .ignoreMethodWithoutComment(false)
                .build();

        TemplateEngineConfig templateEngineConfig = TemplateEngineConfig.builder()
                //文档输出目录
                .fileOutputDir(fileOutputDir)
                //生成文档名称
                .fileName(fileName)
                //生成文档类型
                .documentType(documentType)
                //模板引擎类型
                .templateEngineType(TemplateEngineType.FREEMARKER)
                .build();

        MainConfig config = MainConfig.builder()
                //待生成的项目根路径列表
                .projectPaths(projectPaths)
                //遍历目录的最大深度(默认Integer.MAX_VALUE)
                .maxDepth(Integer.MAX_VALUE)
                //项目名称
                .projectName(projectName)
                //文档版本
                .version(version)
                //文档描述
                .description(description)
                //解析private的类和成员(默认true)
                .includePrivate(true)
                //忽略解析JavaDoc时的错误(默认true)
                .ignoreJavaDocError(true)
                //模板引擎配置
                .templateEngineConfig(templateEngineConfig)
                //文档处理器配置
                .processConfig(processConfig)
                .build();
        return config;
    }

    /**
     * 完整配置
     *
     * @return MainConfig
     */
    @SuppressWarnings("unchecked all")
    private static MainConfig allConfig() {
        //忽略包名前缀
        List<String> ignorePackagePrefix = new ArrayList<>();
        ignorePackagePrefix.add("top.");
        //忽略包名后缀
        List<String> ignorePackageSuffix = new ArrayList<>();
        ignorePackageSuffix.add(".naccl");
        //忽略类名前缀
        List<String> ignoreClassPrefix = new ArrayList<>();
        ignoreClassPrefix.add("Test");
        //忽略类名后缀
        List<String> ignoreClassSuffix = new ArrayList<>();
        ignoreClassSuffix.add("Controller");
        //忽略字段名前缀
        List<String> ignoreFieldPrefix = new ArrayList<>();
        ignoreFieldPrefix.add("is");
        //忽略字段名后缀
        List<String> ignoreFieldSuffix = new ArrayList<>();
        ignoreFieldSuffix.add("Suffix");
        //忽略方法名前缀
        List<String> ignoreMethodPrefix = new ArrayList<>();
        ignoreMethodPrefix.add("get");
        //忽略方法名后缀
        List<String> ignoreMethodSuffix = new ArrayList<>();
        ignoreMethodSuffix.add("Suffix");

        ProcessConfig processConfig = ProcessConfig.builder()
                //是否显示访问修饰符(字段、方法)
                .displayAccessModifier(true)
                //忽略没有字段和方法的类
                .ignoreClassWithoutFieldAndMethod(false)
                //忽略没有JavaDoc注释的字段
                .ignoreFieldWithoutComment(false)
                //忽略没有JavaDoc注释的方法
                .ignoreMethodWithoutComment(false)
                //忽略包名前缀
                .ignorePackagePrefix(ignorePackagePrefix)
                //忽略包名后缀
                .ignorePackageSuffix(ignorePackageSuffix)
                //忽略类名前缀
                .ignoreClassPrefix(ignoreClassPrefix)
                //忽略类名后缀
                .ignoreClassSuffix(ignoreClassSuffix)
                //忽略字段名前缀
                .ignoreFieldPrefix(ignoreFieldPrefix)
                //忽略字段名后缀
                .ignoreFieldSuffix(ignoreFieldSuffix)
                //忽略方法名前缀
                .ignoreMethodPrefix(ignoreMethodPrefix)
                //忽略方法名后缀
                .ignoreMethodSuffix(ignoreMethodSuffix)
                .build();

        TemplateEngineConfig templateEngineConfig = TemplateEngineConfig.builder()
                //文档输出目录
                .fileOutputDir("/Users/naccl/Desktop")
                //生成文档名称
                .fileName("Java2Doc")
                //生成文档类型
                .documentType(DocumentType.WORD)
                //模板引擎类型
                .templateEngineType(TemplateEngineType.FREEMARKER)
                //自定义模板路径(需要自己编写模板,否则使用默认即可)
                .customTemplatePath("/Users/naccl/Desktop/test_document_word.ftl")
                .build();

        //待生成的项目根路径列表
        //可以添加多个项目或模块到List中，将会在同一个文档中一起生成
        //如果只需要生成单个模块或目录下的Java文件，则路径精确到对应目录即可，精确到单个Java文件亦可
        List<String> projectPaths = new ArrayList<>();
        projectPaths.add("/Users/naccl/work/idea-project/Java2Doc");
        //projectPaths.add("/Users/naccl/Desktop/spring-framework");
        //projectPaths.add("/Users/naccl/Desktop/spring-boot");

        MainConfig config = MainConfig.builder()
                //待生成的项目根路径列表
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
                .templateEngineConfig(templateEngineConfig)
                //文档处理器配置
                .processConfig(processConfig)
                .build();
        return config;
    }
}
