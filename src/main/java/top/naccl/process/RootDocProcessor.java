package top.naccl.process;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.RootDoc;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import top.naccl.MainConfig;
import top.naccl.model.ClassModel;
import top.naccl.model.FieldModel;
import top.naccl.model.MethodModel;
import top.naccl.model.PackageModel;
import top.naccl.model.ParamModel;
import top.naccl.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaDoc处理器
 *
 * @author: Naccl
 * @date: 2022-04-18
 */
@Slf4j
public class RootDocProcessor extends AbstractProcessor {
    /**
     * JavaDoc中的未知类型
     */
    private static final String UNKNOWN_TYPE = "<any>";
    /**
     * JavaDoc中的未知类型的替代显示
     */
    private static final String UNKNOWN_TYPE_REPLACE = "T";
    /**
     * 待处理的JavaDoc
     */
    private RootDoc rootDoc;
    /**
     * 处理计数
     */
    private int count = 0;
    /**
     * 处理器配置
     */
    private final ProcessConfig processConfig;

    public RootDocProcessor(MainConfig config) {
        super(config);
        this.processConfig = config.getProcessConfig();
    }

    /**
     * 将JavaDoc设置到处理器中
     *
     * @param rootDoc JavaDoc
     * @return 是否成功
     */
    public boolean setRootDoc(RootDoc rootDoc) {
        //判断RootDoc是否为空，判断和上一次设置的是否是同一个对象，如果是，则可能是JavaDoc解析失败，直接返回false跳过这个
        if (rootDoc == null || rootDoc.equals(this.rootDoc)) {
            log.warn("RootDocProcessor.setRootDoc: rootDoc is the same, ignore");
            return false;
        }
        this.rootDoc = rootDoc;
        this.count++;
        log.info("Java file count: {}, classes: {}", count, this.rootDoc.classes());
        return true;
    }

    /**
     * 转换JavaDoc到数据模型
     */
    public void process() {
        if (this.rootDoc != null) {
            ClassDoc[] classes = this.rootDoc.classes();
            for (ClassDoc classDoc : classes) {
                String packageName = classDoc.containingPackage().name();

                if (isIgnoreWithPrefixOrSuffix(packageName, processConfig.getIgnorePackagePrefix(), processConfig.getIgnorePackageSuffix())) {
                    //忽略包
                    continue;
                }
                if (isIgnoreWithPrefixOrSuffix(classDoc.name(), processConfig.getIgnoreClassPrefix(), processConfig.getIgnoreClassSuffix())) {
                    //忽略类
                    continue;
                }

                if (!packageMap.containsKey(packageName)) {
                    //该包名不存在，创建
                    PackageModel packageModel = new PackageModel(packageName, new ArrayList<>());
                    packageMap.put(packageName, packageModel);
                }

                List<FieldModel> fieldList = new ArrayList<>();
                List<MethodModel> methodList = new ArrayList<>();

                for (FieldDoc fieldDoc : classDoc.fields()) {
                    String comment = getDocComment(fieldDoc);
                    if (processConfig.isIgnoreFieldWithoutComment() && comment.length() == 0) {
                        //忽略没有注释的字段
                        continue;
                    }
                    if (isIgnoreWithPrefixOrSuffix(fieldDoc.name(), processConfig.getIgnoreFieldPrefix(), processConfig.getIgnoreFieldSuffix())) {
                        //忽略字段
                        continue;
                    }

                    FieldModel fieldModel = new FieldModel(getAccessModifier(fieldDoc), fieldDoc.name(), replaceUnknownType(fieldDoc.type().typeName()), comment);
                    fieldList.add(fieldModel);
                }

                for (MethodDoc methodDoc : classDoc.methods()) {
                    String comment = getDocComment(methodDoc);
                    if (processConfig.isIgnoreMethodWithoutComment() && comment.length() == 0) {
                        //忽略没有注释的方法
                        continue;
                    }
                    if (isIgnoreWithPrefixOrSuffix(methodDoc.name(), processConfig.getIgnoreMethodPrefix(), processConfig.getIgnoreMethodSuffix())) {
                        //忽略方法
                        continue;
                    }

                    List<ParamModel> paramList = new ArrayList<>();
                    for (Parameter parameter : methodDoc.parameters()) {
                        ParamModel paramModel = new ParamModel(parameter.name(), replaceUnknownType(parameter.typeName()));
                        paramList.add(paramModel);
                    }

                    MethodModel methodModel = new MethodModel(getAccessModifier(methodDoc), methodDoc.name(), replaceUnknownType(methodDoc.returnType().typeName()), comment, paramList);
                    methodList.add(methodModel);
                }
                if (isIgnoreClass(fieldList, methodList)) {
                    //忽略没有字段和方法的类
                    continue;
                }
                ClassModel classModel = new ClassModel(classDoc.name(), fieldList, methodList);
                packageMap.get(packageName).getClassList().add(classModel);
            }
        }
    }

    /**
     * 判断给定的字符串是否包含在前缀或后缀List中
     *
     * @param str        待判断的字符串
     * @param prefixList 前缀List
     * @param suffixList 后缀List
     * @return 是否包含
     */
    private boolean isIgnoreWithPrefixOrSuffix(String str, List<String> prefixList, List<String> suffixList) {
        if (CollectionUtils.isNotEmpty(prefixList)) {
            for (String prefix : prefixList) {
                if (str.startsWith(prefix)) {
                    return true;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(suffixList)) {
            for (String suffix : suffixList) {
                if (str.endsWith(suffix)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否要忽略没有字段和方法的类
     *
     * @param fieldList  字段列表
     * @param methodList 方法列表
     * @return 是否忽略
     */
    private boolean isIgnoreClass(List<FieldModel> fieldList, List<MethodModel> methodList) {
        return processConfig.isIgnoreClassWithoutFieldAndMethod() && fieldList.size() == 0 && methodList.size() == 0;
    }

    /**
     * 获取访问修饰符
     *
     * @param doc JavaDoc
     * @param <T> {@link ProgramElementDoc}
     * @return 访问修饰符
     */
    private <T extends ProgramElementDoc> String getAccessModifier(T doc) {
        if (processConfig.isDisplayAccessModifier()) {
            if (doc.isPublic()) {
                return "public";
            } else if (doc.isPrivate()) {
                return "private";
            } else if (doc.isProtected()) {
                return "protected";
            }
        }
        return "";
    }

    /**
     * 替换未知类型
     *
     * @param typeName 类型名称
     * @return {@link RootDocProcessor#UNKNOWN_TYPE_REPLACE}
     */
    private String replaceUnknownType(String typeName) {
        return typeName.replaceAll(UNKNOWN_TYPE, UNKNOWN_TYPE_REPLACE);
    }

    /**
     * 获取并处理注释中的特殊字符
     *
     * @param doc {@link Doc} 需要获取注释的Doc节点
     * @param <T> {@link Doc} Doc的子类
     * @return 处理后的注释
     */
    private <T extends Doc> String getDocComment(T doc) {
        String comment = doc.commentText().trim();
        comment = comment.replaceAll("<p>", " ");
        //将多个连续的空白字符替换成一个空格
        comment = comment.replaceAll("\\s+", " ");
        //转义xml字符
        return StringEscapeUtils.escapeXml10(comment);
    }
}
