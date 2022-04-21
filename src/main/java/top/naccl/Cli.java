package top.naccl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import top.naccl.engine.DocumentType;
import top.naccl.engine.TemplateEngineType;
import top.naccl.execute.DocumentExecute;

import java.util.Arrays;

/**
 * CLI执行类
 *
 * @author: Naccl
 * @date: 2022-04-21
 */
@Slf4j
public class Cli {
    /**
     * 命令行配置
     */
    private Options options;
    /**
     * 用法提示
     */
    private static final String USAGE = "java -jar java2doc.jar -p path1[;path2;path3;...] [-n <projectname>] [-v <version>] [-d <desc>] [-o <outputdir>] [-f <filename>] [-t <doctype>]";
    /**
     * 命令行参数列表
     */
    private static final String OPTION_HELP = "help";
    private static final String OPTION_PROJECT_PATHS = "project-paths";
    private static final String OPTION_MAX_DEPTH = "max-depth";
    private static final String OPTION_PROJECT_NAME = "project-name";
    private static final String OPTION_VERSION = "version";
    private static final String OPTION_DESCRIPTION = "description";
    private static final String OPTION_INCLUDE_PRIVATE = "include-private";
    private static final String OPTION_IGNORE_ERROR = "ignore-error";
    private static final String OPTION_OUTPUT_DIR = "output-dir";
    private static final String OPTION_FILENAME = "filename";
    private static final String OPTION_DOCTYPE = "doctype";
    private static final String OPTION_ENGINE_TYPE = "engine-type";
    private static final String OPTION_CUSTOM_TEMPLATE = "custom-template";
    private static final String OPTION_ACCESS_MODIFIER = "access-modifier";
    private static final String OPTION_IGNORE_EMPTY_CLASS = "ignore-empty-class";
    private static final String OPTION_IGNORE_NOCOMMENT_FIELD = "ignore-nocomment-field";
    private static final String OPTION_IGNORE_NOCOMMENT_METHOD = "ignore-nocomment-method";
    private static final String OPTION_IGNORE_PKG_PREFIX = "ignore-pkg-prefix";
    private static final String OPTION_IGNORE_PKG_SUFFIX = "ignore-pkg-suffix";
    private static final String OPTION_IGNORE_CLASS_PREFIX = "ignore-class-prefix";
    private static final String OPTION_IGNORE_CLASS_SUFFIX = "ignore-class-suffix";
    private static final String OPTION_IGNORE_FIELD_PREFIX = "ignore-field-prefix";
    private static final String OPTION_IGNORE_FIELD_SUFFIX = "ignore-field-suffix";
    private static final String OPTION_IGNORE_METHOD_PREFIX = "ignore-method-prefix";
    private static final String OPTION_IGNORE_METHOD_SUFFIX = "ignore-method-suffix";


    /**
     * CLI入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        Cli cli = new Cli();
        MainConfig mainConfig = null;
        try {
            CommandLine cmd = cli.initCli(args);
            mainConfig = cli.generateConfig(cmd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            cli.printHelp();
            System.exit(22);
        }

        log.debug("MainConfig: {}", mainConfig);
        if (mainConfig == null) {
            log.error("An error occurred while generating the configuration");
            return;
        }
        //执行文档生成
        new DocumentExecute(mainConfig).execute();
    }

    /**
     * 初始化命令行接口
     *
     * @param args 命令行参数
     */
    private CommandLine initCli(String[] args) throws ParseException {
        this.options = initOptions();
        return new DefaultParser().parse(options, args);
    }

    /**
     * 打印帮助信息
     */
    private void printHelp() {
        new HelpFormatter().printHelp(150, USAGE, null, options, null);
    }

    /**
     * 初始化命令行选项
     *
     * @return 命令行选项配置
     */
    @SuppressWarnings("unckecked all")
    private Options initOptions() {
        Option help = Option.builder("h")
                .longOpt(OPTION_HELP)
                .desc("This usage help")
                .required(false)
                .hasArg(false)
                .build();
        Option projectPaths = Option.builder("p")
                .longOpt(OPTION_PROJECT_PATHS)
                .argName("pathlist")
                .desc("The list of project paths you want to generate, separated by ';'")
                .required(true)
                .hasArgs()
                .optionalArg(false)
                .valueSeparator(';')
                .build();
        Option maxDepth = Option.builder()
                .longOpt(OPTION_MAX_DEPTH)
                .argName("number")
                .desc("The maximum depth to traverse a directory starting from project path")
                .required(false)
                .hasArg(true)
                .optionalArg(false)
                .type(Integer.class)
                .build();
        Option projectName = Option.builder("n")
                .longOpt(OPTION_PROJECT_NAME)
                .argName("name")
                .desc("The name of your project")
                .required(false)
                .hasArg(true)
                .optionalArg(false)
                .build();
        Option version = Option.builder("v")
                .longOpt(OPTION_VERSION)
                .argName("version")
                .desc("The version of your project")
                .required(false)
                .hasArg(true)
                .optionalArg(false)
                .build();
        Option description = Option.builder("d")
                .longOpt(OPTION_DESCRIPTION)
                .argName("desc")
                .desc("The description of your project")
                .required(false)
                .hasArg(true)
                .optionalArg(false)
                .build();
        Option includePrivate = Option.builder()
                .longOpt(OPTION_INCLUDE_PRIVATE)
                .argName("true/false")
                .desc("Parsing JavaDoc includes private classes and members, default is true")
                .required(false)
                .hasArg(true)
                .optionalArg(false)
                .type(Boolean.class)
                .build();
        Option ignoreJavaDocError = Option.builder()
                .longOpt(OPTION_IGNORE_ERROR)
                .argName("true/false")
                .desc("Ignore errors in parsing JavaDoc, default is true")
                .required(false)
                .hasArg(true)
                .optionalArg(false)
                .type(Boolean.class)
                .build();
        Option fileOutputDir = Option.builder("o")
                .longOpt(OPTION_OUTPUT_DIR)
                .argName("path")
                .desc("Document output directory, default is current directory")
                .required(false)
                .hasArg(true)
                .optionalArg(false)
                .build();
        Option fileName = Option.builder("f")
                .longOpt(OPTION_FILENAME)
                .argName("name")
                .desc("Document file name, default is 'Java2Doc'")
                .required(false)
                .hasArg(true)
                .optionalArg(false)
                .build();
        Option documentType = Option.builder("t")
                .longOpt(OPTION_DOCTYPE)
                .argName("type")
                .desc("Document type, can be 'word', 'html', 'md', default is 'word'")
                .required(false)
                .hasArg(true)
                .optionalArg(false)
                .build();
        Option templateEngineType = Option.builder()
                .longOpt(OPTION_ENGINE_TYPE)
                .argName("type")
                .desc("Template engine type, can be 'freemarker'")
                .required(false)
                .hasArg(true)
                .optionalArg(false)
                .build();
        Option customTemplatePath = Option.builder()
                .longOpt(OPTION_CUSTOM_TEMPLATE)
                .argName("path")
                .desc("Custom template path")
                .required(false)
                .hasArg(true)
                .optionalArg(false)
                .build();
        Option displayAccessModifier = Option.builder()
                .longOpt(OPTION_ACCESS_MODIFIER)
                .argName("true/false")
                .desc("Display access modifier, default is true")
                .required(false)
                .hasArg(true)
                .optionalArg(false)
                .type(Boolean.class)
                .build();
        Option ignoreClassWithoutFieldAndMethod = Option.builder()
                .longOpt(OPTION_IGNORE_EMPTY_CLASS)
                .argName("true/false")
                .desc("Ignore classes without field and method, default is false")
                .required(false)
                .hasArg(true)
                .optionalArg(false)
                .type(Boolean.class)
                .build();
        Option ignoreFieldWithoutComment = Option.builder()
                .longOpt(OPTION_IGNORE_NOCOMMENT_FIELD)
                .argName("true/false")
                .desc("Ignore fields without comment, default is false")
                .required(false)
                .hasArg(true)
                .optionalArg(false)
                .type(Boolean.class)
                .build();
        Option ignoreMethodWithoutComment = Option.builder()
                .longOpt(OPTION_IGNORE_NOCOMMENT_METHOD)
                .argName("true/false")
                .desc("Ignore methods without comment, default is false")
                .required(false)
                .hasArg(true)
                .optionalArg(false)
                .type(Boolean.class)
                .build();
        Option ignorePackagePrefix = Option.builder()
                .longOpt(OPTION_IGNORE_PKG_PREFIX)
                .argName("prefixlist")
                .desc("The ignore list of package prefix, separated by ';'")
                .required(false)
                .hasArgs()
                .optionalArg(false)
                .valueSeparator(';')
                .build();
        Option ignorePackageSuffix = Option.builder()
                .longOpt(OPTION_IGNORE_PKG_SUFFIX)
                .argName("suffixlist")
                .desc("The ignore list of package suffix, separated by ';'")
                .required(false)
                .hasArgs()
                .optionalArg(false)
                .valueSeparator(';')
                .build();
        Option ignoreClassPrefix = Option.builder()
                .longOpt(OPTION_IGNORE_CLASS_PREFIX)
                .argName("prefixlist")
                .desc("The ignore list of class prefix, separated by ';'")
                .required(false)
                .hasArgs()
                .optionalArg(false)
                .valueSeparator(';')
                .build();
        Option ignoreClassSuffix = Option.builder()
                .longOpt(OPTION_IGNORE_CLASS_SUFFIX)
                .argName("suffixlist")
                .desc("The ignore list of class suffix, separated by ';'")
                .required(false)
                .hasArgs()
                .optionalArg(false)
                .valueSeparator(';')
                .build();
        Option ignoreFieldPrefix = Option.builder()
                .longOpt(OPTION_IGNORE_FIELD_PREFIX)
                .argName("prefixlist")
                .desc("The ignore list of field prefix, separated by ';'")
                .required(false)
                .hasArgs()
                .optionalArg(false)
                .valueSeparator(';')
                .build();
        Option ignoreFieldSuffix = Option.builder()
                .longOpt(OPTION_IGNORE_FIELD_SUFFIX)
                .argName("suffixlist")
                .desc("The ignore list of field suffix, separated by ';'")
                .required(false)
                .hasArgs()
                .optionalArg(false)
                .valueSeparator(';')
                .build();
        Option ignoreMethodPrefix = Option.builder()
                .longOpt(OPTION_IGNORE_METHOD_PREFIX)
                .argName("prefixlist")
                .desc("The ignore list of method prefix, separated by ';'")
                .required(false)
                .hasArgs()
                .optionalArg(false)
                .valueSeparator(';')
                .build();
        Option ignoreMethodSuffix = Option.builder()
                .longOpt(OPTION_IGNORE_METHOD_SUFFIX)
                .argName("suffixlist")
                .desc("The ignore list of method suffix, separated by ';'")
                .required(false)
                .hasArgs()
                .optionalArg(false)
                .valueSeparator(';')
                .build();

        return new Options()
                .addOption(help)
                .addOption(projectPaths)
                .addOption(maxDepth)
                .addOption(projectName)
                .addOption(version)
                .addOption(description)
                .addOption(includePrivate)
                .addOption(ignoreJavaDocError)
                .addOption(fileOutputDir)
                .addOption(fileName)
                .addOption(documentType)
                .addOption(templateEngineType)
                .addOption(customTemplatePath)
                .addOption(displayAccessModifier)
                .addOption(ignoreClassWithoutFieldAndMethod)
                .addOption(ignoreFieldWithoutComment)
                .addOption(ignoreMethodWithoutComment)
                .addOption(ignorePackagePrefix)
                .addOption(ignorePackageSuffix)
                .addOption(ignoreClassPrefix)
                .addOption(ignoreClassSuffix)
                .addOption(ignoreFieldPrefix)
                .addOption(ignoreFieldSuffix)
                .addOption(ignoreMethodPrefix)
                .addOption(ignoreMethodSuffix)
                ;
    }

    /**
     * 处理命令行参数，并生成配置
     *
     * @param cmd 命令行参数解析结果
     * @return 配置
     */
    private MainConfig generateConfig(CommandLine cmd) {
        if (cmd.hasOption(OPTION_HELP)) {
            printHelp();
            System.exit(0);
        }

        // 项目路径是必须的参数
        if (!cmd.hasOption(OPTION_PROJECT_PATHS)) {
            System.out.println("Missing required option: p");
            printHelp();
            System.exit(22);
        }
        String[] projectPaths = cmd.getOptionValues(OPTION_PROJECT_PATHS);
        MainConfig config = ConfigFactory.defaultMainConfig(Arrays.asList(projectPaths));

        if (cmd.hasOption(OPTION_MAX_DEPTH)) {
            int maxDepth = Integer.parseInt(cmd.getOptionValue(OPTION_MAX_DEPTH));
            config.setMaxDepth(maxDepth);
        }
        if (cmd.hasOption(OPTION_PROJECT_NAME)) {
            String projectName = cmd.getOptionValue(OPTION_PROJECT_NAME);
            config.setProjectName(projectName);
        }
        if (cmd.hasOption(OPTION_VERSION)) {
            String version = cmd.getOptionValue(OPTION_VERSION);
            config.setVersion(version);
        }
        if (cmd.hasOption(OPTION_DESCRIPTION)) {
            String description = cmd.getOptionValue(OPTION_DESCRIPTION);
            config.setDescription(description);
        }
        if (cmd.hasOption(OPTION_INCLUDE_PRIVATE)) {
            boolean includePrivate = Boolean.parseBoolean(cmd.getOptionValue(OPTION_INCLUDE_PRIVATE));
            config.setIncludePrivate(includePrivate);
        }
        if (cmd.hasOption(OPTION_IGNORE_ERROR)) {
            boolean ignoreJavaDocError = Boolean.parseBoolean(cmd.getOptionValue(OPTION_IGNORE_ERROR));
            config.setIgnoreJavaDocError(ignoreJavaDocError);
        }
        if (cmd.hasOption(OPTION_OUTPUT_DIR)) {
            String fileOutputDir = cmd.getOptionValue(OPTION_OUTPUT_DIR);
            config.getTemplateEngineConfig().setFileOutputDir(fileOutputDir);
        }
        if (cmd.hasOption(OPTION_FILENAME)) {
            String fileName = cmd.getOptionValue(OPTION_FILENAME);
            config.getTemplateEngineConfig().setFileName(fileName);
        }
        if (cmd.hasOption(OPTION_DOCTYPE)) {
            String doctype = cmd.getOptionValue(OPTION_DOCTYPE);
            DocumentType documentType = DocumentType.valueOf(doctype.toUpperCase());
            config.getTemplateEngineConfig().setDocumentType(documentType);
        }
        if (cmd.hasOption(OPTION_ENGINE_TYPE)) {
            String templateEngineType = cmd.getOptionValue(OPTION_ENGINE_TYPE);
            TemplateEngineType engineType = TemplateEngineType.valueOf(templateEngineType.toUpperCase());
            config.getTemplateEngineConfig().setTemplateEngineType(engineType);
        }
        if (cmd.hasOption(OPTION_CUSTOM_TEMPLATE)) {
            String customTemplatePath = cmd.getOptionValue(OPTION_CUSTOM_TEMPLATE);
            config.getTemplateEngineConfig().setCustomTemplatePath(customTemplatePath);
        }
        if (cmd.hasOption(OPTION_ACCESS_MODIFIER)) {
            boolean displayAccessModifier = Boolean.parseBoolean(cmd.getOptionValue(OPTION_ACCESS_MODIFIER));
            config.getProcessConfig().setDisplayAccessModifier(displayAccessModifier);
        }
        if (cmd.hasOption(OPTION_IGNORE_EMPTY_CLASS)) {
            boolean ignoreEmptyClass = Boolean.parseBoolean(cmd.getOptionValue(OPTION_IGNORE_EMPTY_CLASS));
            config.getProcessConfig().setIgnoreClassWithoutFieldAndMethod(ignoreEmptyClass);
        }
        if (cmd.hasOption(OPTION_IGNORE_NOCOMMENT_FIELD)) {
            boolean ignoreNoCommentField = Boolean.parseBoolean(cmd.getOptionValue(OPTION_IGNORE_NOCOMMENT_FIELD));
            config.getProcessConfig().setIgnoreFieldWithoutComment(ignoreNoCommentField);
        }
        if (cmd.hasOption(OPTION_IGNORE_NOCOMMENT_METHOD)) {
            boolean ignoreNoCommentMethod = Boolean.parseBoolean(cmd.getOptionValue(OPTION_IGNORE_NOCOMMENT_METHOD));
            config.getProcessConfig().setIgnoreMethodWithoutComment(ignoreNoCommentMethod);
        }
        if (cmd.hasOption(OPTION_IGNORE_PKG_PREFIX)) {
            String[] ignorePkgPrefix = cmd.getOptionValues(OPTION_IGNORE_PKG_PREFIX);
            config.getProcessConfig().setIgnorePackagePrefix(Arrays.asList(ignorePkgPrefix));
        }
        if (cmd.hasOption(OPTION_IGNORE_PKG_SUFFIX)) {
            String[] ignorePkgSuffix = cmd.getOptionValues(OPTION_IGNORE_PKG_SUFFIX);
            config.getProcessConfig().setIgnorePackageSuffix(Arrays.asList(ignorePkgSuffix));
        }
        if (cmd.hasOption(OPTION_IGNORE_CLASS_PREFIX)) {
            String[] ignoreClassPrefix = cmd.getOptionValues(OPTION_IGNORE_CLASS_PREFIX);
            config.getProcessConfig().setIgnoreClassPrefix(Arrays.asList(ignoreClassPrefix));
        }
        if (cmd.hasOption(OPTION_IGNORE_CLASS_SUFFIX)) {
            String[] ignoreClassSuffix = cmd.getOptionValues(OPTION_IGNORE_CLASS_SUFFIX);
            config.getProcessConfig().setIgnoreClassSuffix(Arrays.asList(ignoreClassSuffix));
        }
        if (cmd.hasOption(OPTION_IGNORE_FIELD_PREFIX)) {
            String[] ignoreFieldPrefix = cmd.getOptionValues(OPTION_IGNORE_FIELD_PREFIX);
            config.getProcessConfig().setIgnoreFieldPrefix(Arrays.asList(ignoreFieldPrefix));
        }
        if (cmd.hasOption(OPTION_IGNORE_FIELD_SUFFIX)) {
            String[] ignoreFieldSuffix = cmd.getOptionValues(OPTION_IGNORE_FIELD_SUFFIX);
            config.getProcessConfig().setIgnoreFieldSuffix(Arrays.asList(ignoreFieldSuffix));
        }
        if (cmd.hasOption(OPTION_IGNORE_METHOD_PREFIX)) {
            String[] ignoreMethodPrefix = cmd.getOptionValues(OPTION_IGNORE_METHOD_PREFIX);
            config.getProcessConfig().setIgnoreMethodPrefix(Arrays.asList(ignoreMethodPrefix));
        }
        if (cmd.hasOption(OPTION_IGNORE_METHOD_SUFFIX)) {
            String[] ignoreMethodSuffix = cmd.getOptionValues(OPTION_IGNORE_METHOD_SUFFIX);
            config.getProcessConfig().setIgnoreMethodSuffix(Arrays.asList(ignoreMethodSuffix));
        }
        return config;
    }
}
