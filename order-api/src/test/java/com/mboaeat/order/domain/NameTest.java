package com.mboaeat.order.domain;

import org.apache.commons.lang3.builder.DiffResult;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NameTest {

    @Test
    public void createAndNoDiff(){
        Name name1 = Name.builder().nameFr("Test fr").build();
        Name name2 = Name.builder().nameFr("Test fr").build();
        final DiffResult diffResult = name1.diff(name2);

        assertThat(diffResult.getNumberOfDiffs()).isEqualTo(0);
    }

    @Test
    public void createAnd_Two_Diff(){
        Name name1 = Name.builder().nameFr("Test fr").build();
        Name name2 = Name.builder().nameFr("Test test fr").nameEn("Test en").build();
        final DiffResult diffResult = name1.diff(name2);

        assertThat(diffResult.getNumberOfDiffs()).isEqualTo(2);
    }

}