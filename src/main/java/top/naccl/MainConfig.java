package top.naccl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.naccl.engine.TemplateEngineConfig;
import top.naccl.process.ProcessConfig;

import java.util.List;

/**
 * 主配置
 *
 * @author: Naccl
 * @date: 2022-04-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainConfig {
    /**
     * 待生成的项目根路径列表
     */
    private List<String> projectPaths;
    /**
     * 遍历目录的最大深度(默认Integer.MAX_VALUE)
     */
    private int maxDepth;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 文档版本
     */
    private String version;
    /**
     * 文档描述
     */
    private String description;
    /**
     * 解析private的类和成员(默认true)
     */
    private boolean includePrivate;
    /**
     * 忽略解析JavaDoc时的错误(默认true)(未设置"-classpath"，待解析的Java类中的依赖包将找不到，会产生很多错误输出，但不影响文档生成，完全可以忽略)
     */
    private boolean ignoreJavaDocError;
    /**
     * 模板引擎配置
     */
    private TemplateEngineConfig templateEngineConfig;
    /**
     * 文档处理器配置
     */
    private ProcessConfig processConfig;
}
