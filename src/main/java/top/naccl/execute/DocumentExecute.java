package top.naccl.execute;

import com.sun.javadoc.RootDoc;
import lombok.extern.slf4j.Slf4j;
import top.naccl.MainConfig;
import top.naccl.engine.TemplateEngine;
import top.naccl.engine.TemplateEngineFactory;
import top.naccl.model.ProjectModel;
import top.naccl.process.ProjectModelProcessor;
import top.naccl.process.RootDocProcessor;
import top.naccl.reader.JavaDocReader;
import top.naccl.util.CollectionUtils;
import top.naccl.util.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文档生成执行器
 *
 * @author: Naccl
 * @date: 2022-04-19
 */
@Slf4j
public class DocumentExecute {
    /**
     * 主配置
     */
    private final MainConfig config;

    public DocumentExecute(MainConfig config) {
        this.config = config;
    }

    /**
     * 执行文档生成
     */
    public void execute() {
        try {
            long start = System.currentTimeMillis();
            List<String> javaFiles = getJavaFiles(config.getProjectPaths(), config.getMaxDepth());
            if (CollectionUtils.isEmpty(javaFiles)) {
                log.warn("No Java files were found in the path you input");
                return;
            }
            log.info("The number of Java files found in the path you input is: {}", javaFiles.size());

            parseJavaDoc(javaFiles);

            ProjectModel projectModel = new ProjectModelProcessor(config).process();
            log.debug("The ProjectModel is: {}", projectModel);

            TemplateEngine templateEngine = new TemplateEngineFactory(config).newInstance();
            templateEngine.produce(projectModel);

            log.info("The document was generated successfully, the time consumed is: {}ms", System.currentTimeMillis() - start);
        } catch (IOException e) {
            log.error("The document was generated failed, the error is:", e);
        }
    }

    /**
     * 遍历给定路径中的Java文件
     *
     * @param pathList 路径列表
     * @param maxDepth 最大深度
     * @return java文件列表
     */
    private List<String> getJavaFiles(List<String> pathList, int maxDepth) throws IOException {
        List<String> javaFiles = new ArrayList<>();
        for (String path : pathList) {
            if (!FileUtils.isFileExists(path)) {
                log.error("One of the project path to be generated does not exist, the absolute path you input is: {}", FileUtils.getCanonicalPath(path));
                return null;
            }
            log.info("The project path to be generated is: {}", FileUtils.getCanonicalPath(path));
            javaFiles.addAll(FileUtils.walkJavaFile(path, maxDepth));
        }
        return javaFiles;
    }

    /**
     * 解析JavaDoc
     *
     * @param javaFiles 待解析的Java文件列表
     */
    private void parseJavaDoc(List<String> javaFiles) {
        RootDocProcessor rootDocProcessor = new RootDocProcessor(config);
        javaFiles.forEach(file -> {
            RootDoc rootDoc = JavaDocReader.read(file, config);
            if (rootDocProcessor.setRootDoc(rootDoc)) {
                rootDocProcessor.process();
            }
        });
        log.info("Parse JavaDoc DONE");
    }
}
