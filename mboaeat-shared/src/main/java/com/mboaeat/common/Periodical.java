package com.mboaeat.common;

public interface Periodical<PERIOD extends AbstractPeriod> extends Comparable<Periodical<PERIOD>> {

    PERIOD getPeriod();
}
