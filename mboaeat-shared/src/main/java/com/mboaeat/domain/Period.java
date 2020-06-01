package com.mboaeat.domain;

import java.time.LocalDate;
import java.util.Objects;

public interface Period {

    LocalDate getStartDate();

    LocalDate getEndDate();

    boolean appliesToday();

    boolean isInThePast();

    boolean isInTheFuture();

    boolean contains(LocalDate isContain);

    boolean overlapsWith(Period period);

    default boolean isOpenEnded(){
        return Objects.isNull(getEndDate());
    }

}
