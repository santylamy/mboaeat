package com.mboaeat.order.domain;

import com.mboaeat.domain.AbstractPeriod;
import com.mboaeat.domain.Period;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.mboaeat.domain.CollectionsUtils.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PeriodByDayTest {

    @Test
    public void periodByDay_withPeriod_returnsExpectedPeriod(){
        LocalDate startDate = LocalDate.of(2000, 1, 1);
        LocalDate endDate = LocalDate.of(2000, 1, 2);

        Period period = mock(Period.class);
        when(period.getStartDate()).thenReturn(startDate);
        when(period.getEndDate()).thenReturn(endDate);

        assertThat(new PeriodByDay(period).getStartDate()).isEqualTo(startDate);
        assertThat(new PeriodByDay(period).getEndDate()).isEqualTo(endDate);
    }

    @Test
    public void isOpenEnded_endDateIsNull_ReturnTrue(){
        PeriodByDay period = PeriodByDay.builder().startDate(LocalDate.of(2019, 1, 1)).build();
        assertThat(period.isOpenEnded()).isTrue();
    }

    @Test
    public void isOpenEnded_endDateIsNotNull_ReturnFalse(){
        PeriodByDay period = PeriodByDay
                .builder()
                .startDate(LocalDate.of(2019, 1, 1))
                .endDate(LocalDate.of(2019, 1, 1))
                .build();
        assertThat(period.isOpenEnded()).isFalse();
    }

    @Test
    public void splitByMonth_withPartialOverlap(){

        PeriodByDay period = PeriodByDay
                .builder()
                .startDate(LocalDate.of(1990, 1, 1))
                .endDate(LocalDate.of(2000, 1, 28))
                .build();

        PeriodByDay period2 = PeriodByDay
                .builder()
                .startDate(LocalDate.of(2000, 1, 28))
                .build();

        PeriodByDay period3 = PeriodByDay
                .builder()
                .startDate(LocalDate.of(1990, 1, 1))
                .endDate(LocalDate.of(2000, 1, 27))
                .build();

        PeriodByDay period4 = PeriodByDay
                .builder()
                .startDate(LocalDate.of(2000, 1, 28))
                .endDate(LocalDate.of(2000, 1, 28))
                .build();

        List<PeriodByDay> originalPeriods = newArrayList(period);
        List<PeriodByDay> periodToSplitOn = newArrayList(period2);
        List<PeriodByDay> expectedResult = newArrayList(period3, period4);

        assertThat(AbstractPeriod.slplitByMonth(originalPeriods, periodToSplitOn)).isEqualTo(expectedResult);
    }

}