package top.naccl.util;

import java.util.Collection;

/**
 * 集合工具类
 *
 * @author: Naccl
 * @date: 2022-04-19
 */
public class CollectionUtils {
    /**
     * 集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 集合是否为非空
     *
     * @param collection 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
