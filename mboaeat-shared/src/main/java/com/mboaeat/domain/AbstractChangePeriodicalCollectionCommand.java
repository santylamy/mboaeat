package com.mboaeat.domain;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractChangePeriodicalCollectionCommand<PERIODICAL extends PeriodicalElement<PERIOD>, PERIOD extends AbstractPeriod>
        implements ChangePeriodicalCollectionCommand<PERIODICAL, PERIOD>{

    public AbstractChangePeriodicalCollectionCommand(){}

    public AbstractChangePeriodicalCollectionCommand(PERIOD period) {
        this.period = period;
    }

    private PERIOD period;

    @Override
    public PERIOD getPeriod() {
        return period;
    }

    static <PERIOD extends AbstractPeriod> Collection<PERIOD> toPeriods(Collection<? extends ChangePeriodicalCollectionCommand<?, PERIOD>> changeCommands){
        return (Collection<PERIOD>) changeCommands.stream().map(TO_PERIOD).collect(Collectors.toList());
    }


    private static Function<ChangePeriodicalCollectionCommand<?,?>, AbstractPeriod> TO_PERIOD =
            new Function<ChangePeriodicalCollectionCommand<?, ?>, AbstractPeriod>() {
        @Override
        public AbstractPeriod apply(ChangePeriodicalCollectionCommand<?, ?> changePeriodicalCollectionCommand) {
            return changePeriodicalCollectionCommand.getPeriod();
        }
    };

}
