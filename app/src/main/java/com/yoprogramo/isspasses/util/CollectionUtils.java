package com.yoprogramo.isspasses.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class CollectionUtils {

    private CollectionUtils() {
        // hide constructor
    }

    public static boolean isEmpty(final Collection< ? > coll) {
        return coll == null || coll.isEmpty();
    }

    public static int size(final Collection<?> coll) {
        return isEmpty(coll) ? 0 : coll.size();
    }

    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
    public static <T> List<T> singletonList(T item) {
        final List<T> list = new ArrayList<>();
        list.add(item);
        return list;
    }

    public static <T> T firstElement(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    public static boolean contains(final Set<String> strings, final String string) {
        return !isEmpty(strings) && (strings.contains(string) ||
                strings.contains(string.toLowerCase(Locale.US)) ||
                strings.contains(string.toUpperCase(Locale.US)));
    }

    /**
     * Returns List of the List having size of param size
     *
     * @param list input list to be portioned
     * @param size maximum size of each partition
     * @param <T>  Generic type of the List
     * @return A list of Lists which is portioned from the original list
     */
    public static <T> List<List<T>> splitListBySize(List<T> list, int size) {
        final List<List<T>> chunkList = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            chunkList.add(list.subList(i, i + size >= list.size() ? list.size() : i + size));
        }
        return chunkList;
    }
}
