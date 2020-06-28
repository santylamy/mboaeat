package com.mboaeat.order.domain;

import com.mboaeat.domain.TranslatableString;
import org.apache.commons.lang3.builder.DiffResult;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NameTest {

    @Test
    public void createAndNoDiff(){
        TranslatableString name1 = TranslatableString.builder().french("Test fr").build();
        TranslatableString name2 = TranslatableString.builder().french("Test fr").build();
        final DiffResult diffResult = name1.diff(name2);

        assertThat(diffResult.getNumberOfDiffs()).isEqualTo(0);
    }

    @Test
    public void createAnd_Two_Diff(){
        TranslatableString name1 = TranslatableString.builder().french("Test fr").build();
        TranslatableString name2 = TranslatableString.builder().french("Test test fr").english("Test en").build();
        final DiffResult diffResult = name1.diff(name2);

        assertThat(diffResult.getNumberOfDiffs()).isEqualTo(2);
    }

}