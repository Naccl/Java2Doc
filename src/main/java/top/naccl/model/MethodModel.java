package top.naccl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 方法信息
 *
 * @author: Naccl
 * @date: 2022-04-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodModel {
    /**
     * 访问修饰符
     */
    private String accessModifier;
    /**
     * 方法名
     */
    private String name;
    /**
     * 返回类型
     */
    private String returnType;
    /**
     * JavaDoc注释
     */
    private String comment;
    /**
     * 参数列表
     */
    private List<ParamModel> paramList;
}
