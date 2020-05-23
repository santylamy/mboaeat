package com.mboaeat.common;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.Validate;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mboaeat.common.CollectionsUtils.newArrayList;
import static com.mboaeat.common.CollectionsUtils.newHashSet;
import static com.mboaeat.common.LocalDateUtils.nextDay;
import static com.mboaeat.common.LocalDateUtils.previousDay;
import static com.mboaeat.common.PeriodicalUtils.filterOutNonOverlapping;
import static java.util.stream.Stream.concat;
import static org.apache.commons.lang3.Validate.isTrue;

@SuperBuilder
@NoArgsConstructor
public abstract class AbstractPeriod implements Period, Comparable<AbstractPeriod> {

    public static final Comparator<AbstractPeriod> BY_PERIOD_ORDERING = (o1, o2) -> o1.compareTo(o2);
    public static Comparator<AbstractPeriod> PERIOD_OVERLAPS_COMPARATOR = (o1, o2) -> {
        if (o1.overlapsWith(o2)){
            return 0;
        }
        return BY_PERIOD_ORDERING.compare(o1, o2);
    };

    public static <PERIOD extends AbstractPeriod> Function<Periodical<PERIOD>, PERIOD> toPeriod() {
        return new Function<Periodical<PERIOD>, PERIOD>() {
            @Override
            public PERIOD apply(Periodical<PERIOD> periodPeriodical) {
                return periodPeriodical.getPeriod();
            }
        };
    }

    public static <PERIOD extends AbstractPeriod> List<PERIOD> slplitByMonth(List<PERIOD> periodsToSplit, List<PERIOD> periodsToSplitWith) {
        if (periodsToSplitWith.isEmpty() || periodsToSplit.isEmpty()){
            return periodsToSplit;
        }

        Collections.sort(periodsToSplit);
        Collections.sort(periodsToSplitWith);

        PERIOD first = periodsToSplit
                .stream()
                .sorted()
                .findFirst()
                .orElse(null);;
        PERIOD last =  periodsToSplitWith
                .stream()
                .sorted()
                .reduce((periodical, periodical2) -> periodical2)
                .orElse(null);

        PERIOD overlapping = (PERIOD) first.create(first.getStartDate(), last.getEndDate());
        List<PERIOD> result = overlapping.splitOverlappingPeriods(
                Stream.concat(periodsToSplit.stream(), periodsToSplitWith.stream()).collect(Collectors.toList())
        );

        return filterOutNonOverlapping(periodsToSplit, result);
    }

    public  <PERIOD_TYPE extends AbstractPeriod> List<PERIOD_TYPE> splitOverlappingPeriods(List<PERIOD_TYPE> periodsToSplit){
        if (periodsToSplit.isEmpty()){
            return List.of();
        }
        Set<AbstractPeriod> allPeriods = concat(Stream.of(this), periodsToSplit.stream()).collect(Collectors.toSet());

        Collection<PERIOD_TYPE> allSplittedPeriods = toPeriods(allStartAndEndDates(allPeriods));

        List<PERIOD_TYPE> result = newArrayList();

        for (PERIOD_TYPE splittedPeriod: allSplittedPeriods){
            if (this.contains(splittedPeriod)){
                result.add(splittedPeriod);
            }
        }

        return result;
    }

    private <PERIOD_TYPE extends AbstractPeriod> Collection<PERIOD_TYPE> toPeriods(List<Boundary> boundaries) {
        Collection<PERIOD_TYPE> result = newArrayList();
        for (int i = 0; i < boundaries.size(); i = i + 2){
            result.add((PERIOD_TYPE) create(boundaries.get(i).localDate, boundaries.get(i + 1).localDate));
        }
        return result;
    }

    private List<Boundary> allStartAndEndDates(Collection<AbstractPeriod> allPeriods) {
        Set<Boundary> boundaries = newHashSet();
        for (AbstractPeriod period: allPeriods){
            boundaries.add(Boundary.builder().localDate(period.getStartDate()).type(BoundaryType.START).build());
            boundaries.add(Boundary.builder().localDate(previousDay(period.getStartDate())).type(BoundaryType.END).build());
            boundaries.add(Boundary.builder().localDate(period.getEndDate()).type(BoundaryType.END).build());
            if (period.getEndDate() != null){
                boundaries.add(Boundary.builder().localDate(nextDay(period.getEndDate())).type(BoundaryType.START).build());
            }
        }
        List boundariesSorted = boundaries
                .stream().
                        sorted(Comparator.comparing(Boundary::getLocalDate, Comparator.nullsFirst(Comparator.reverseOrder())))
                .collect(Collectors.toList());
        return boundariesSorted.subList(1, endIndex(boundaries));
    }

    private int endIndex(Collection<Boundary> boundaries) {
        return boundaries.contains(
                Boundary.builder().localDate(null).type(BoundaryType.END).build()
        ) ? boundaries.size() : boundaries.size() - 1;
    }


    public abstract LocalDate getStartDate();

    public abstract LocalDate getEndDate();

    protected abstract AbstractPeriod create(LocalDate startDate, LocalDate endDate);

    @Override
    public boolean appliesToday() {
        return (isEqualsOrBeforeToday(getStartDate()) && isEqualsOrAfterToday(getEndDate()));
    }

    @Override
    public boolean isInThePast() {
        return LocalDate.now().isBefore(getEndDate());
    }

    @Override
    public boolean isInTheFuture() {
        return LocalDate.now().isAfter(getStartDate());
    }

    @Override
    public boolean contains(LocalDate isContaining) {
        if (isContaining == null){
            return isOpenEnded();
        }
        LocalDate today = LocalDate.now();
        if (isOpenEnded()){
            return today.isAfter(getStartDate()) || isContaining.equals(getStartDate());
        }
        return (today.isAfter(getStartDate()) || today.equals(getStartDate()))
                && (today.isBefore(getEndDate()) || today.equals(getEndDate()));
    }

    @Override
    public int compareTo(AbstractPeriod o) {
        return getStartDate().compareTo(o.getStartDate());
    }


    public <PERIOD_TYPE extends AbstractPeriod> List<PERIOD_TYPE> remove(PERIOD_TYPE periodToRemove){
        List<PERIOD_TYPE> splits = split(periodToRemove);
        return splits
                .stream()
                .filter(period_type -> !period_type.overlapsWith(periodToRemove))
                .collect(Collectors.toList());

    }

    public boolean overlapsWith(Period period){
        return period != null && ( contains(period.getStartDate()) || period.contains(getStartDate()) );
    }

    public <PERIOD extends Period, PERIOD_TYPE extends AbstractPeriod> PERIOD_TYPE merge(PERIOD_TYPE period){
        isTrue(isConnected(period), "Can only merge connected periods");
        return applyMergeAlgorithm(period);
    }

    private <PERIOD_TYPE extends AbstractPeriod> PERIOD_TYPE applyMergeAlgorithm(PERIOD_TYPE period) {
        LocalDate thisStartDate = getStartDate();
        LocalDate otherStartDate = period.getStartDate();
        LocalDate thisEndDate = getEndDate();
        LocalDate otherEndDate = period.getEndDate();

        LocalDate newStartDate = thisStartDate.isBefore(otherStartDate) ? thisStartDate : otherStartDate;

        LocalDate newEndDate = null;

        if (thisEndDate != null && otherEndDate != null){
            newEndDate = thisEndDate.isAfter(otherEndDate) ? thisEndDate : otherEndDate;
        }

        return (PERIOD_TYPE) create(newStartDate, newEndDate);
    }

    public <PERIOD_TYPE extends AbstractPeriod> boolean isConnected(PERIOD_TYPE period){
        if (isOpenEnded() && period.isOpenEnded()){
            return true;
        }

        if (isOpenEnded()){
            return period.getEndDate().equals(previousDay(getStartDate()));
        }

        if (period.isOpenEnded()){
            return getEndDate().equals(previousDay(period.getStartDate()));
        }
        return getStartDate().equals(nextDay(period.getEndDate())) || period.getStartDate().equals(nextDay(getEndDate()));
    }

    public <PERIOD_TYPE extends AbstractPeriod> List<PERIOD_TYPE> split(PERIOD_TYPE periodToSplitOn){
        LocalDate localStartDate = getStartDate();
        LocalDate localEndDate = getEndDate();

        if (!overlapsWith(periodToSplitOn)){
            return (List<PERIOD_TYPE>) List.of(create(localStartDate, localEndDate));
        }
        if (periodToSplitOn.contains(self())){
            return (List<PERIOD_TYPE>) List.of(create(localStartDate, localEndDate));
        }

        List<PERIOD_TYPE> splittedPeriods = List.of();
        LocalDate localSplitStartDate = periodToSplitOn.getStartDate();
        LocalDate localSplitEndDate = periodToSplitOn.getEndDate();

        boolean containsLocalSplitEndDate = contains(localSplitEndDate);
        boolean periodStartsWithinThisPeriod = periodStartsWithinThisPeriod(periodToSplitOn);
        boolean periodStartBeforeThisPeriod = periodStartBeforeThisPeriod(periodToSplitOn);

        if (periodStartsWithinThisPeriod){
            splittedPeriods.add((PERIOD_TYPE) create(localStartDate, localSplitStartDate.minusDays(1)));
        }

        if (periodStartBeforeThisPeriod){
            splittedPeriods.add((PERIOD_TYPE) create(localSplitStartDate, localSplitEndDate));
        }

        if (periodStartBeforeThisPeriod && containsLocalSplitEndDate){
            splittedPeriods.add((PERIOD_TYPE) create(localStartDate, localEndDate));
        }

        if (containsLocalSplitEndDate && !periodToSplitOn.isOpenEnded() && !localSplitEndDate.equals(localEndDate)){
            splittedPeriods.add((PERIOD_TYPE) create(localSplitEndDate.plusDays(1), localEndDate));
        }

        if (!containsLocalSplitEndDate && contains(localSplitStartDate)){
            splittedPeriods.add((PERIOD_TYPE) create(localSplitStartDate, localEndDate));
        }

        return splittedPeriods;
    }

    public <PERIOD_TYPE extends AbstractPeriod> boolean periodStartBeforeThisPeriod(PERIOD_TYPE period){
        return ( !contains(period.getStartDate()) || getStartDate().equals(period.getStartDate()) );
    }

    public <PERIOD_TYPE extends AbstractPeriod> boolean periodStartsWithinThisPeriod(PERIOD_TYPE period){
        LocalDate localStartDate = getStartDate();
        return contains(localStartDate) && !getStartDate().equals(localStartDate);
    }

    public <PERIOD_TYPE extends AbstractPeriod> boolean contains(PERIOD_TYPE period){
        return contains(period.getStartDate()) && contains(period.getEndDate());
    }

    private  <PERIOD_TYPE extends AbstractPeriod> PERIOD_TYPE self(){
        return (PERIOD_TYPE) this;
    }

    protected static boolean isEqualsOrBeforeToday(LocalDate localDate) {
        LocalDate today = LocalDate.now();
        return (today.equals(localDate) || localDate.isAfter(today));
    }

    protected static boolean isEqualsOrAfterToday(LocalDate localDate) {
        LocalDate today = LocalDate.now();
        return (isEqualsOrAfter(today, localDate));
    }

    private static boolean isEqualsOrAfter(LocalDate earlierDate,
                                           LocalDate laterDate) {
        return (earlierDate.equals(laterDate) || ( laterDate == null ? true : laterDate.isBefore(earlierDate)) );
    }

    @Data
    @Builder
    private static class Boundary implements Comparable<Boundary>{
        private LocalDate localDate;
        private BoundaryType type;

        private Boundary(LocalDate startDate, BoundaryType type){
            localDate = null;
            if (startDate != null){
                LocalDate date = LocalDate.from(startDate);
                localDate = date;
            }
            this.type = type;
        }

        @Override
        public int compareTo(AbstractPeriod.Boundary o) {
            int equalDates = this.localDate.compareTo(o.localDate);
            if (equalDates == 0){
                return this.type.compareTo(o.type);
            }
            return equalDates;
        }
    }

    private enum  BoundaryType {
        START, END
    }
}
