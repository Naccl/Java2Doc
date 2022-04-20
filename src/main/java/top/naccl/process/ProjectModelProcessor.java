package top.naccl.process;

import top.naccl.MainConfig;
import top.naccl.model.ProjectModel;

import java.util.ArrayList;

/**
 * 数据模型处理器
 *
 * @author: Naccl
 * @date: 2022-04-18
 */
public class ProjectModelProcessor extends AbstractProcessor {
    public ProjectModelProcessor(MainConfig config) {
        super(config);
    }

    /**
     * 生成数据模型
     *
     * @return 数据模型
     */
    public ProjectModel process() {
        ProjectModel projectModel = new ProjectModel();
        projectModel.setProjectName(config.getProjectName());
        projectModel.setVersion(config.getVersion());
        projectModel.setDescription(config.getDescription());
        projectModel.setPackageList(new ArrayList<>(packageMap.values()));
        return projectModel;
    }
}
