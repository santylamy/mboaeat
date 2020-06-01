package com.mboaeat.domain;

public interface Periodical<PERIOD extends AbstractPeriod> extends Comparable<Periodical<PERIOD>> {

    PERIOD getPeriod();
}
