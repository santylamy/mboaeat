package com.mboaeat.order.domain;

import com.mboaeat.common.AbstractPeriod;
import com.mboaeat.common.Period;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Data
@SuperBuilder
//@NoArgsConstructor
//@AllArgsConstructor
@Embeddable
public class PeriodByDay extends AbstractPeriod {

    @Column(name = "START_DATE")
    private LocalDate startDate;
    @Column(name = "END_DATE")
    private LocalDate endDate;

    public PeriodByDay(){}

    public PeriodByDay(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public PeriodByDay(LocalDate localDate){
        this(localDate, null);
    }

    public PeriodByDay(Period period){
        this(period.getStartDate(), period.getEndDate());
    }

    public static PeriodByDay periodByDayNullable(){
        return PeriodByDay.builder().build();
    }

    public static PeriodByDay periodByDayStartingToday(){
        return new PeriodByDay(LocalDate.now());
    }

    @Override
    protected AbstractPeriod create(LocalDate startDate, LocalDate endDate) {
        return PeriodByDay.builder().startDate(startDate).endDate(endDate).build();
    }
}
