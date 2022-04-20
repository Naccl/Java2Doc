package top.naccl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 项目信息
 *
 * @author: Naccl
 * @date: 2022-04-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectModel {
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
     * 项目中所有的包
     */
    private List<PackageModel> packageList;
}
