package com.mboaeat.domain;

public interface ChangePeriodicalCollectionCommand<PERIODICAL extends PeriodicalElement<PERIOD>, PERIOD extends AbstractPeriod> {

    PERIOD getPeriod();

    PERIODICAL change(PERIODICAL periodical);

    PERIODICAL create(PERIOD period);

}
