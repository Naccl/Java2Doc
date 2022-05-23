<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width initial-scale=1">
  <style>
    body {
      background-color: #ffffff;
      font-size: 1rem;
      font-family: "Open Sans", "Clear Sans", "Helvetica Neue", Helvetica, Arial, 'Segoe UI Emoji', sans-serif;
      -webkit-font-smoothing: antialiased;
      tab-size: 4;
      color: #333333;
      line-height: 1.6;
      word-break: normal;
      word-wrap: break-word;
      white-space: normal;
      max-width: 860px;
      margin: 0 auto;
      padding: 10px 30px 100px;
      box-sizing: border-box;
    }

    @media only screen and (min-width: 1400px) {
      body {
        max-width: 1024px;
      }
    }

    @media only screen and (min-width: 1800px) {
      body {
        max-width: 1200px;
      }
    }

    figure {
      overflow-x: auto;
      margin: 1.2em 0;
      max-width: calc(100% + 16px);
    }

    table {
      margin: 0;
      border-collapse: collapse;
      border-spacing: 0;
      width: 100%;
      overflow: auto;
      break-inside: auto;
      text-align: left;
      padding: 0;
      word-break: initial;
    }

    table th {
      font-weight: bold;
      border: 1px solid #dfe2e5;
      border-bottom: 0;
      margin: 0;
      padding: 6px 13px;
    }

    table tr {
      break-inside: avoid;
      break-after: auto;
      border: 1px solid #dfe2e5;
      margin: 0;
      padding: 0;
    }

    table tr:nth-child(2n), thead {
      background-color: #f8f8f8;
    }

    table td {
      border: 1px solid #dfe2e5;
      margin: 0;
      padding: 6px 13px;
    }

    p {
      orphans: 4;
      margin: 0.8em 0;
    }

    h1, h2, h3, h4, h5, h6 {
      break-after: avoid-page;
      break-inside: avoid;
      orphans: 4;
      margin-top: 1rem;
      margin-bottom: 1rem;
      font-weight: bold;
      line-height: 1.4;
    }

    h1 {
      text-align: center;
      font-size: 2.25em;
      line-height: 1.2;
      border-bottom: 1px solid #eee;
    }

    h2 {
      font-size: 1.75em;
      line-height: 1.225;
      border-bottom: 1px solid #eee;
    }

    h3 {
      font-size: 1.5em;
      line-height: 1.43;
    }

    h4 {
      font-size: 1.25em;
    }

    h5 {
      font-size: 1em;
    }

    h6 {
      font-size: 1em;
      color: #777;
    }
  </style>
  <title>${projectName!'Java2Doc'}</title>
</head>
<body>
<h1>Java Class描述文档</h1>
<p><strong>项目名称：</strong>${projectName!'Java2Doc'}</p>
<p><strong>文档版本：</strong>${version!'1.0.0'}</p>
<p><strong>文档描述：</strong>${description!'Java项目Class描述文档生成'}</p>
<#list packageList as package>
  <h4>${package.name}包中类的描述</h4>
  <figure>
    <table>
      <thead>
      <tr>
        <th style="text-align:center;"><span>类名</span></th>
        <th style="text-align:center;"><span>属性/方法</span></th>
        <th style="text-align:center;"><span>描述</span></th>
      </tr>
      </thead>
      <tbody>
      <#list package.classList as class>
          <#if class.fieldList?size == 0 && class.methodList?size == 0>
          <#--如果字段和方法都没有，就保留个类名-->
            <tr>
              <td style="text-align:center;">${class.name}</td>
              <td></td>
              <td></td>
            </tr>
          </#if>
          <#list class.fieldList as field>
              <#if field_index == 0>
              <#--第一行-->
                <tr>
                  <td style="text-align:center;" rowspan="${class.fieldList?size + class.methodList?size}">${class.name}</td>
                  <td><#if field.accessModifier!=''>${field.accessModifier} </#if>${field.type} ${field.name};</td>
                  <td>${field.comment}</td>
                </tr>
              <#else>
              <#--第一行之后-->
                <tr>
                  <td><#if field.accessModifier!=''>${field.accessModifier} </#if>${field.type} ${field.name};</td>
                  <td>${field.comment}</td>
                </tr>
              </#if>
          </#list>
          <#list class.methodList as method>
              <#if class.fieldList?size == 0 && method_index == 0>
              <#--第一行-->
                <tr>
                  <td style="text-align:center;" rowspan="${class.methodList?size}">${class.name}</td>
                  <td><#if method.accessModifier!=''>${method.accessModifier} </#if>${method.returnType} ${method.name}(<#list method.paramList as param>${param.type} ${param.name}<#if param_has_next>, </#if></#list>);</td>
                  <td>${method.comment}</td>
                </tr>
              <#else>
              <#--第一行之后-->
                <tr>
                  <td><#if method.accessModifier!=''>${method.accessModifier} </#if>${method.returnType} ${method.name}(<#list method.paramList as param>${param.type} ${param.name}<#if param_has_next>, </#if></#list>);</td>
                  <td>${method.comment}</td>
                </tr>
              </#if>
          </#list>
      </#list>
      </tbody>
    </table>
  </figure>
</#list>
</body>
</html>