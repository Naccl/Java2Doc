package top.naccl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 参数信息
 *
 * @author: Naccl
 * @date: 2022-04-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamModel {
    /**
     * 参数名称
     */
    private String name;
    /**
     * 参数类型
     */
    private String type;
}
