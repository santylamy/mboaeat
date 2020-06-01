package com.mboaeat.domain;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;

import static java.time.temporal.ChronoField.DAY_OF_YEAR;

public class LocalDateUtils {

    public static TemporalAdjuster nextDayOfYear = temporal -> temporal.with(DAY_OF_YEAR, 1);

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
