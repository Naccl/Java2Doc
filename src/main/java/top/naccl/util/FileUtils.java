package top.naccl.util;

import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 文件工具类
 *
 * @author: Naccl
 * @date: 2022-04-18
 */
public class FileUtils {
    /**
     * 获取当前路径
     *
     * @return 当前路径
     */
    public static String getCurrentPath() {
        return System.getProperty("user.dir");
    }

    /**
     * 检查文件或目录是否存在
     *
     * @param path 文件或目录路径
     * @return 存在返回true，否则返回false
     */
    public static boolean isFileExists(String path) {
        return Files.exists(Paths.get(path));
    }

    /**
     * 获取绝对路径
     *
     * @param path 路径
     * @return 绝对路径
     */
    @SneakyThrows
    public static String getCanonicalPath(String path) {
        File file = new File(path);
        return file.getCanonicalPath();
    }

    /**
     * 创建文件
     *
     * @param path     文件路径
     * @param fileName 文件名
     * @return File Object
     */
    public static File touchFile(String path, String fileName) {
        File folder = new File(path);
        folder.mkdirs();
        return new File(folder, fileName);
    }

    /**
     * 遍历给定根目录下的所有指定拓展名文件
     *
     * @param rootPath      根目录
     * @param maxDepth      最大深度
     * @param fileExtension 文件拓展名
     * @return 指定拓展名文件列表
     * @throws IOException 出现IO异常则抛出
     */
    public static List<String> walkFile(String rootPath, int maxDepth, String fileExtension) throws IOException {
        List<String> javaFiles = new ArrayList<>();
        Stream<Path> paths = Files.walk(Paths.get(rootPath), maxDepth);
        paths.map(Path::toString).filter(f -> f.endsWith(fileExtension)).forEach(javaFiles::add);
        return javaFiles;
    }

    /**
     * 遍历给定根目录下的所有.java文件
     *
     * @param rootPath 根目录
     * @param maxDepth 最大深度
     * @return java文件列表
     * @throws IOException 出现IO异常则抛出
     */
    public static List<String> walkJavaFile(String rootPath, int maxDepth) throws IOException {
        return walkFile(rootPath, maxDepth, ".java");
    }

    /**
     * 遍历给定根目录下的所有.java文件
     *
     * @param rootPath 根目录
     * @return java文件列表
     * @throws IOException 出现IO异常则抛出
     */
    public static List<String> walkJavaFile(String rootPath) throws IOException {
        return walkJavaFile(rootPath, Integer.MAX_VALUE);
    }
}
