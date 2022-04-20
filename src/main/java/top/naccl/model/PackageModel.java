package top.naccl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 包信息
 *
 * @author: Naccl
 * @date: 2022-04-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageModel {
    /**
     * 包名
     */
    private String name;
    /**
     * 包中所有的类
     */
    private List<ClassModel> classList;
}
