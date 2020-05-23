package com.mboaeat.common;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.mboaeat.common.CollectionsUtils.*;
import static java.util.stream.StreamSupport.*;

public abstract class AbstractPeriodicalCollection<PERIODICAL extends PeriodicalElement<PERIOD>, PERIOD extends AbstractPeriod> implements PeriodicalCollection<PERIODICAL, PERIOD> {

    private static Function<Periodical<?>, AbstractPeriod> TO_PERIOD = new Function<Periodical<?>, AbstractPeriod>() {
        @Override
        public AbstractPeriod apply(Periodical<?> periodical) {
            return periodical.getPeriod();
        }
    };

    protected abstract List<PERIODICAL> getPeriodicals();

    public abstract AbstractPeriodicalCollection<PERIODICAL, PERIOD> copy();

    public PERIODICAL getCurrent(){
        return getPeriodicals()
                .stream()
                .filter(periodical -> periodical.getPeriod().appliesToday())
                .findFirst().orElse(null);
    }

    public boolean isEmpty(){
        return getPeriodicals().isEmpty();
    }

    public int size(){
        return getPeriodicals().size();
    }

    public PERIODICAL getFirst(){
        if (isEmpty()){
            return null;
        }
        return toList()
                .stream()
                .sorted()
                .findFirst()
                .orElse(null);
    }

    public PERIODICAL getLast(){
        if (isEmpty()){
            return null;
        }
        return toList()
                .stream()
                .sorted()
                .reduce((periodical, periodical2) -> periodical2)
                .orElse(null);
    }

    @Override
    public List<PERIODICAL> apply(ChangePeriodicalCollectionCommand<PERIODICAL, PERIOD> command, boolean commit) {
        return apply(List.of(command), commit);
    }

    final protected boolean commit(List<PERIODICAL> result){
        return commitChangeElementsKeepUnchangedElements(result);
    }

    private boolean commitChangeElementsKeepUnchangedElements(List<PERIODICAL> result) {
        boolean change = false;
        Map<PERIOD, PERIODICAL> mapWithNewPeriodicals =
                result.stream().collect(Collectors.toMap(periodical -> periodical.getPeriod(), periodical -> periodical));

        Iterator<PERIODICAL> iterator = this.getPeriodicals().iterator();

        while (iterator.hasNext()){
            PERIODICAL periodical = iterator.next();
            PERIODICAL newPeriodical = mapWithNewPeriodicals.get(periodical.getPeriod());
            if (newPeriodical != null && newPeriodical.isContentEqual(periodical)){
                mapWithNewPeriodicals.remove(periodical.getPeriod());
            } else {
                deleteElement(iterator, periodical);
                change = true;
            }
        }
        if (mapWithNewPeriodicals.isEmpty()){
            return change;
        } else {
            addElementsToPotentiallySortedList(getPeriodicals(), mapWithNewPeriodicals.values());
            return true;
        }
    }

    private void addElementsToPotentiallySortedList(List<PERIODICAL> periodicals, Collection<PERIODICAL> newValues) {
        newValues.stream()
                .forEach(
                        periodical -> {
                            int binarySearchResult = PeriodicalUtils.binarySearchIndexOverlappingPeriod(periodicals, periodical.getPeriod());
                            periodicals.add(determineBinarySearchInsertionPoint(binarySearchResult), periodical);
                        }
                );
    }

    private int determineBinarySearchInsertionPoint(int binarySearchResult) {
        return binarySearchResult < 0 ? -binarySearchResult - 1 : binarySearchResult ;
    }

    protected void deleteElement(Iterator<PERIODICAL> iterator, PERIODICAL periodical) {
        iterator.remove();
    }

    protected static <PERIODICAL extends PeriodicalElement<PERIOD>, PERIOD extends AbstractPeriod> List<PERIODICAL> merge(List<PERIODICAL> periodicals) {
        List<PERIODICAL> result = newArrayList();

        if (periodicals.isEmpty()){
            return result;
        }

        Collections.sort(periodicals);

        Iterator<PERIODICAL> iterator = periodicals.iterator();
        PERIODICAL previousPeriod = iterator.next();

        while (iterator.hasNext()){
            PERIODICAL period = iterator.next();
            if (previousPeriod.isContentEqual(period) && previousPeriod.getPeriod().isConnected(period.getPeriod())){
                previousPeriod = (PERIODICAL) previousPeriod.merge(period);
            } else {
                result.add(previousPeriod);
                previousPeriod = period;
            }
        }

        if (previousPeriod != null){
            result.add(previousPeriod);
        }

        return result;
    }

    public List<PERIODICAL> apply(List<? extends ChangePeriodicalCollectionCommand<PERIODICAL, PERIOD>> changeCommands, boolean commit){
        return apply3(changeCommands, commit);
    }

    List<PERIODICAL> apply3(List<? extends ChangePeriodicalCollectionCommand<PERIODICAL,PERIOD>> changeCommands, boolean commit){
        List<PERIODICAL> result = merge(apply2(changeCommands));
        if (commit){
            commit(result);
        }
        return result;
    }


    List<PERIODICAL> apply2(List<? extends ChangePeriodicalCollectionCommand<PERIODICAL, PERIOD>> changeCommands){

        List<PERIODICAL> result = split(stream(AbstractChangePeriodicalCollectionCommand.toPeriods(changeCommands).spliterator(), false).collect(Collectors.toList()));

        for (ChangePeriodicalCollectionCommand<PERIODICAL, PERIOD> changeCommand: changeCommands){
            result = apply1(changeCommand, result);
        }

        return result;
    }

    private List<PERIODICAL> apply1(ChangePeriodicalCollectionCommand<PERIODICAL, PERIOD> changeCommand, List<PERIODICAL> splittedPeriodicals){
        Set<PERIODICAL> result = newHashSet();

        for (PERIODICAL splittedPeriodical: splittedPeriodicals) {
            if (splittedPeriodical.getPeriod().overlapsWith(changeCommand.getPeriod())){
                PERIODICAL changedPeriodical = changeCommand.change(splittedPeriodical);
                boolean changedObjectNotReallyChanged = splittedPeriodical == changedPeriodical;
                if (changedObjectNotReallyChanged) {
                    changedPeriodical = PeriodicalUtils.copy(changedPeriodical);
                }
                result.add(changedPeriodical);
            }else {
                result.add(splittedPeriodical);
            }
        }

        return new ArrayList<>(result);
    }

    @Override
    public List<PERIODICAL> remove(PERIOD period) {
        List<PERIODICAL> splits = split(period);
        return splits
                .stream()
                .filter(periodical -> !periodical.getPeriod().overlapsWith(period))
                .collect(Collectors.toList());
    }

    @Override
    public List<PERIODICAL> toList() {
        return List.copyOf(getPeriodicals());
    }

    @Override
    public List<PERIOD> toPeriods() {
        return getPeriodicals()
                .stream()
                .map(periodical -> periodical.getPeriod())
                .collect(Collectors.toList());
    }

    protected List<PERIODICAL> split(PERIOD period){
        List<PERIODICAL> result = newArrayList();

        for (PERIODICAL periodical: getPeriodicals()){
            List<PERIOD> splittedPeriods = periodical.getPeriod().split(period);
            result.addAll(copyPeriodicalForPeriods(periodical, splittedPeriods));
        }

        return result;
    }

    protected List<PERIODICAL> split(List<PERIOD> periodsToSplitWith){
        List<PERIODICAL> result = newArrayList();

        List<PERIOD> periodsToSplit = gePeriods();
        List<PERIOD> splittedPeriods = AbstractPeriod.slplitByMonth(periodsToSplit, periodsToSplitWith);

        result.addAll(copyPeriodicalForPeriods(splittedPeriods));

        return result;
    }

    public List<PERIOD> gePeriods() {
        return getPeriodicals().stream().map(periodicalToPeriod()).collect(Collectors.toList());
    }

    private Function<Periodical<PERIOD>, PERIOD> periodicalToPeriod() {
        return (Function<Periodical<PERIOD>, PERIOD>) ((Function) TO_PERIOD );
    }

    private Collection<? extends PERIODICAL> copyPeriodicalForPeriods(PERIODICAL periodical, List<PERIOD> splittedPeriods) {
        List<PERIODICAL> result = newArrayList();
        for (PERIOD splittedPeriod: splittedPeriods){
            result.add((PERIODICAL) periodical.copy(splittedPeriod));
        }
        return result;
    }

    private Collection<? extends PERIODICAL> copyPeriodicalForPeriods(List<PERIOD> periods) {
        List<PERIODICAL> result = newArrayList();
        for (PERIOD period: periods){
            List<PERIODICAL> periodicalsInRange = this.getPeriodicalsInRange(period);
            result.add((PERIODICAL) periodicalsInRange.stream().findFirst().orElseThrow().copy(period));
        }
        return result;
    }

    public List<PERIODICAL> getPeriodicalsInRange(Period period) {
        return getPeriodicals()
                .stream()
                .filter(periodical -> periodical.getPeriod().overlapsWith(period))
                .sorted()
                .collect(Collectors.toList());
    }
}
