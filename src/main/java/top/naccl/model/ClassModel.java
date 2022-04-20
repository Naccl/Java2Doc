package top.naccl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 类信息
 *
 * @author: Naccl
 * @date: 2022-04-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassModel {
    /**
     * 类名
     */
    private String name;
    /**
     * 类中的字段
     */
    private List<FieldModel> fieldList;
    /**
     * 类中的方法
     */
    private List<MethodModel> methodList;
}
