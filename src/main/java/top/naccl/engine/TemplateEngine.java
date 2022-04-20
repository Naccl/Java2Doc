package top.naccl.engine;

import top.naccl.model.ProjectModel;

import java.io.IOException;

/**
 * 模板引擎接口
 *
 * @author: Naccl
 * @date: 2022-04-19
 */
public interface TemplateEngine {
    /**
     * 生成文档
     *
     * @param model 模板数据
     * @throws IOException IO异常直接向上抛出
     */
    void produce(ProjectModel model) throws IOException;
}
