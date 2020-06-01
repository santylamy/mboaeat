package com.mboaeat.domain;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.mboaeat.domain.CollectionsUtils.newArrayList;

public class PeriodicalUtils {

    private static final Function<PeriodicalElement<?>, AbstractPeriod> PERIODICAL_ELEMENT_TO_PERIOD =
            new Function<PeriodicalElement<?>, AbstractPeriod>() {
                @Override
                public AbstractPeriod apply(PeriodicalElement<?> periodicalElement) {
                    return periodicalElement.getPeriod();
                }
            };

    public static <PERIOD extends AbstractPeriod, E extends PeriodicalElement<PERIOD>> E copy(E originalPeriodicalElement){
        return (E) originalPeriodicalElement.copy(originalPeriodicalElement.getPeriod());
    }

    public static <PERIOD extends AbstractPeriod> List<PERIOD> filterOutNonOverlapping(List<PERIOD> periodsForOverlapCheck, List<PERIOD> periodsToFilter) {
        Collections.sort(periodsForOverlapCheck);
        Collections.sort(periodsToFilter);

        List<PERIOD> result = newArrayList();

        for (PERIOD period: periodsToFilter){
            int index = Collections.binarySearch(periodsForOverlapCheck, period, AbstractPeriod.PERIOD_OVERLAPS_COMPARATOR);
            if (index >= 0){
                result.add(period);
            }
        }

        return result;
    }


    public static <PERIODICAL extends PeriodicalElement<PERIOD>, PERIOD extends AbstractPeriod> int binarySearchIndexOverlappingPeriod(
            List<PERIODICAL> sortedNonOverlappingPeriodList, PERIOD searchPeriod) {
        Function<PERIODICAL, AbstractPeriod> function = (Function) PERIODICAL_ELEMENT_TO_PERIOD;
        return binarySearchIndexOverlappingPeriod(sortedNonOverlappingPeriodList, function, searchPeriod);
    }

    private static <PERIODICAL extends PeriodicalElement<PERIOD>, PERIOD extends AbstractPeriod> int binarySearchIndexOverlappingPeriod(
            List<PERIODICAL> sortedNonOverlappingPeriodList, Function<PERIODICAL, AbstractPeriod> toPeriodFunction, AbstractPeriod searchPeriod) {
        List<AbstractPeriod> periodsView = sortedNonOverlappingPeriodList
                .stream()
                .map(toPeriodFunction).collect(Collectors.toList());
        int index = Collections.binarySearch(periodsView, searchPeriod, AbstractPeriod.PERIOD_OVERLAPS_COMPARATOR);
        return index;
    }
}
