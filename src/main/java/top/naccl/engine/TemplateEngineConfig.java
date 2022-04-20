package top.naccl.engine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模板引擎配置
 *
 * @author: Naccl
 * @date: 2022-04-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateEngineConfig {
    /**
     * 文档输出目录
     */
    private String fileOutputDir;
    /**
     * 生成文档名称
     */
    private String fileName;
    /**
     * 生成文档类型
     */
    private DocumentType documentType;
    /**
     * 模板引擎类型
     */
    private TemplateEngineType templateEngineType;
    /**
     * 自定义模板路径
     */
    private String customTemplatePath;
}
