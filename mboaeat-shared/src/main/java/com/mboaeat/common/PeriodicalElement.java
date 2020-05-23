package com.mboaeat.common;

public interface PeriodicalElement<PERIOD extends AbstractPeriod> extends Periodical<PERIOD> {
    PeriodicalElement<PERIOD> copy(PERIOD period);
    default PeriodicalElement<PERIOD> merge(PeriodicalElement<PERIOD> periodical){
        return copy(periodical.getPeriod());
    }

    boolean isContentEqual(PeriodicalElement<PERIOD> periodical);
}
