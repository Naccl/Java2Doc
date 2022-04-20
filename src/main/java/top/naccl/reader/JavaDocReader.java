package top.naccl.reader;

import com.sun.javadoc.RootDoc;
import top.naccl.MainConfig;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaDocReader
 *
 * @author: Naccl
 * @date: 2022-04-18
 */
public class JavaDocReader {
    /**
     * 解析出的JavaDoc结果
     */
    private static RootDoc root;

    public static class Doclet {
        /**
         * The main entry point of the doclet.
         *
         * @param root the root of the documentation tree
         * @return true if the doclet processed the documentation
         */
        @SuppressWarnings("unused")
        public static boolean start(RootDoc root) {
            JavaDocReader.root = root;
            return true;
        }
    }

    /**
     * 读取JavaDoc
     *
     * @param javaFilePath java文件路径
     * @param config       主配置
     * @return 读取结果
     */
    public static RootDoc read(String javaFilePath, MainConfig config) {
        return read(javaFilePath, config.isIncludePrivate(), config.isIgnoreJavaDocError());
    }

    /**
     * 读取JavaDoc
     *
     * @param javaFilePath java文件路径
     * @param readAll      是否读取所有的类和成员(包括private)
     * @param ignoreError  是否忽略错误(未设置"-classpath"，待解析的Java类中的依赖包将找不到，会产生很多错误输出，但不影响文档生成，完全可以忽略)
     * @return 读取结果
     */
    public static RootDoc read(String javaFilePath, boolean readAll, boolean ignoreError) {
        List<String> args = new ArrayList<>();
        args.add("-encoding");
        args.add("utf-8");
        args.add(javaFilePath);
        if (readAll) {
            args.add("-private");
        }

        PrintWriter out;
        if (ignoreError) {
            //忽略错误，将输出流设置为无操作
            out = new PrintWriter(new OutputStream() {
                @Override
                public void write(int b) {
                }
            });
        } else {
            out = new PrintWriter(System.err);
        }

        com.sun.tools.javadoc.Main.execute("Java2Doc", out, out, out, Doclet.class.getName(), args.toArray(new String[0]));
        return root;
    }
}
