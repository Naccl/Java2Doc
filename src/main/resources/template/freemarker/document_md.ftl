# Java Class描述文档

**项目名称**：${projectName!'Java2Doc'}

**文档版本**：${version!'1.0.0'}

**文档描述**：${description!'Java项目Class描述文档生成'}
<#list packageList as package>

#### ${package.name}包中类的描述

| 类名 | 属性/方法 | 描述 |
|:---:|---|---|
<#list package.classList as class>
<#if class.fieldList?size == 0 && class.methodList?size == 0>
<#--如果字段和方法都没有，就保留个类名-->
| ${class.name} | | |
</#if>
<#list class.fieldList as field>
<#if field_index == 0>
<#--第一行-->
| ${class.name} | <#if field.accessModifier!=''>${field.accessModifier} </#if>${field.type} ${field.name}; | ${field.comment} |
<#else>
<#--第一行之后-->
| | <#if field.accessModifier!=''>${field.accessModifier} </#if>${field.type} ${field.name}; | ${field.comment} |
</#if>
</#list>
<#list class.methodList as method>
<#if class.fieldList?size == 0 && method_index == 0>
<#--第一行-->
| ${class.name} | <#if method.accessModifier!=''>${method.accessModifier} </#if>${method.returnType} ${method.name}(<#list method.paramList as param>${param.type} ${param.name}<#if param_has_next>, </#if></#list>); | ${method.comment} |
<#else>
<#--第一行之后-->
| | <#if method.accessModifier!=''>${method.accessModifier} </#if>${method.returnType} ${method.name}(<#list method.paramList as param>${param.type} ${param.name}<#if param_has_next>, </#if></#list>); | ${method.comment} |
</#if>
</#list>
</#list>
</#list>