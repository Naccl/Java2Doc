package top.naccl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字段信息
 *
 * @author: Naccl
 * @date: 2022-04-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldModel {
    /**
     * 访问修饰符
     */
    private String accessModifier;
    /**
     * 字段名
     */
    private String name;
    /**
     * 字段类型
     */
    private String type;
    /**
     * JavaDoc注释
     */
    private String comment;
}
