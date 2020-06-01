package com.mboaeat.order.domain;

import com.mboaeat.common.exception.MboaEatException;
import com.mboaeat.domain.AbstractPeriod;
import com.mboaeat.domain.Period;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Data
@SuperBuilder
@ToString
@Embeddable
public class PeriodByDay extends AbstractPeriod {

    @Column(name = "START_DATE")
    private LocalDate startDate;
    @Column(name = "END_DATE")
    private LocalDate endDate;

    public PeriodByDay(){}

    public PeriodByDay(LocalDate startDate, LocalDate endDate) {
        assertStartDateNotNull(startDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void assertStartDateNotNull(LocalDate startDate) {
        if (startDate == null){
            throw new MboaEatException("startDate_mandatory");
        }
    }

    public static PeriodByDay periodByDayStartingTodayPlusMonth(int month){
        return new PeriodByDay(LocalDate.now().plusMonths(month));
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
    protected PeriodByDay create(LocalDate startDate, LocalDate endDate) {
        return PeriodByDay.builder().startDate(startDate).endDate(endDate).build();
    }

    @Override
    public boolean isOpenEnded() {
        return endDate == null;
    }
}
