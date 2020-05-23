package com.mboaeat.common;

import java.util.List;

public interface PeriodicalCollection<PERIODICAL extends PeriodicalElement<PERIOD>, PERIOD extends AbstractPeriod> {

    List<PERIODICAL> remove(PERIOD period);

    List<PERIODICAL> toList();

    List<PERIOD> toPeriods();

    List<PERIODICAL> apply(ChangePeriodicalCollectionCommand<PERIODICAL, PERIOD> command, boolean commit);
}
