package com.mboaeat.common;

import java.util.*;

public class CollectionsUtils {

    public static List newArrayList(){
        return new ArrayList<>();
    }

    public static Set newHashSet() {
        return new HashSet();
    }

    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<>();
    }
}
