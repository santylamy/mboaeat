package com.mboaeat.common;

import java.time.LocalDate;

public class LocalDateUtils {

    public static LocalDate nextDay(LocalDate localDate) {
        return localDate.plusDays(1);
    }

    public static LocalDate previousDay(LocalDate localDate) {
        return localDate.minusDays(1);
    }

    public static LocalDate nextMonth(LocalDate localDate){
        return localDate.plusMonths(1);
    }

}
