package com.mboaeat.domain;

import java.util.*;

public class CollectionsUtils {

    public static List newArrayList(){
        return new ArrayList<>();
    }

    public static <E> List<E> newArrayList(E... elements){
        List<E> list = new ArrayList<E>(elements.length);
        Collections.addAll(list, elements);
        return list;
    }

    public static Set newHashSet() {
        return new HashSet();
    }

    public static <E> Set<E> newHashSet(E... elements) {
        Set<E> set = new HashSet<>(elements.length);
        Collections.addAll(set, elements);
        return set;
    }

    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static boolean isEmpty(Collection collections) {
        return  (collections == null || collections.isEmpty());
    }

    public static <C> C getLast(Collection<C> collection){
        if (collection == null || collection.isEmpty()){
            return null;
        }
        return collection.stream().reduce((c1, c2) -> c2).get();
    }
}
