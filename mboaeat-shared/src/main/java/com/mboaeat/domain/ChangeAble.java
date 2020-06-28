package com.mboaeat.domain;

import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ReflectionDiffBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public interface ChangeAble<T extends Diffable<T>> extends Diffable<T> {

    default boolean hasChange(T t){
        if (Objects.isNull(t)){
            return false;
        }
        return diff(t).getNumberOfDiffs() >= 1;
    }

    @Override
    default DiffResult diff(T obj){
        return new ReflectionDiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE).build();
    }
}
