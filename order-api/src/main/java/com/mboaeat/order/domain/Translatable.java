package com.mboaeat.order.domain;

import com.mboaeat.common.translation.Language;

public interface Translatable {

    String getFrench();

    String getEnglish();

    boolean hasFrench();

    boolean hasEnglish();

    boolean isBlank();

    boolean isNotBlank();

    String asString(Language language);
}
