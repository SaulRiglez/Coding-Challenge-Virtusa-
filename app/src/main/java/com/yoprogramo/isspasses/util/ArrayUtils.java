package com.yoprogramo.isspasses.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final public class ArrayUtils {

    private ArrayUtils() {
        // hide constructor
    }

    public static <T> boolean isEmpty(final T[] array) {
        return array == null || array.length == 0;
    }

    @SuppressWarnings("ManualArrayToCollectionCopy")
    public static List<String> createListFromResourceArray(Context context, int arrayResourceId) {
        final String[] array = context.getResources().getStringArray(arrayResourceId);
        final List<String> list = new ArrayList<>();
        if (isEmpty(array)) {
            return list;
        }
        for (String str : array) {
            list.add(str);
        }
        return list;
    }

    public static <T>  int size(final T[] array) {
        return isEmpty(array) ? 0 : array.length;
    }

    public static <T> boolean contains(final T[] arrayOfObjects, @Nullable T object) {
        if (ArrayUtils.isEmpty(arrayOfObjects) || object == null) {
            return false;
        }
        if (arrayOfObjects[0].getClass() != object.getClass()) {
            return false;
        }

        final List<T> listOfObject = Arrays.asList(arrayOfObjects);
        return listOfObject.contains(object);
    }

    public static @NonNull
    String[] removeNullsFromArray(@Nullable final String[] stringArray) {
        if (isEmpty(stringArray)) {
            return new String[] {};
        }

        final List<String> listOfNonNullStrings = new ArrayList<>();
        for (String string : stringArray) {
            if (string != null) {
                listOfNonNullStrings.add(string);
            }
        }

        if (listOfNonNullStrings.isEmpty()) {
            return new String[] {};
        }

        return listOfNonNullStrings.toArray(new String[listOfNonNullStrings.size()]);
    }
}
