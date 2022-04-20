package top.naccl.engine;

import lombok.Getter;

/**
 * 文档类型
 *
 * @author: Naccl
 * @date: 2022-04-19
 */
public enum DocumentType {
    /**
     * Microsoft Word
     */
    WORD(".doc", "document_word"),
    /**
     * HTML
     */
    HTML(".html", "document_html"),
    /**
     * Markdown
     */
    MARKDOWN(".md", "document_md");

    /**
     * 生成文件后缀
     */
    @Getter
    private final String generateFileExtension;
    /**
     * 模板文件名
     */
    @Getter
    private final String templateFileName;

    DocumentType(String generateFileExtension, String templateFileName) {
        this.generateFileExtension = generateFileExtension;
        this.templateFileName = templateFileName;
    }
}
