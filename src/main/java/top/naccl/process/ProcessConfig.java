package top.naccl.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 处理器配置
 *
 * @author: Naccl
 * @date: 2022-04-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessConfig {
    /**
     * 是否显示访问修饰符(字段、方法)
     */
    private boolean displayAccessModifier;
    /**
     * 忽略没有字段和方法的类
     */
    private boolean ignoreClassWithoutFieldAndMethod;
    /**
     * 忽略没有JavaDoc注释的字段
     */
    private boolean ignoreFieldWithoutComment;
    /**
     * 忽略没有JavaDoc注释的方法
     */
    private boolean ignoreMethodWithoutComment;
    /**
     * 忽略包名前缀
     */
    private List<String> ignorePackagePrefix;
    /**
     * 忽略包名后缀
     */
    private List<String> ignorePackageSuffix;
    /**
     * 忽略类名前缀
     */
    private List<String> ignoreClassPrefix;
    /**
     * 忽略类名后缀
     */
    private List<String> ignoreClassSuffix;
    /**
     * 忽略字段名前缀
     */
    private List<String> ignoreFieldPrefix;
    /**
     * 忽略字段名后缀
     */
    private List<String> ignoreFieldSuffix;
    /**
     * 忽略方法名前缀
     */
    private List<String> ignoreMethodPrefix;
    /**
     * 忽略方法名后缀
     */
    private List<String> ignoreMethodSuffix;
}
