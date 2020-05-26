package com.mboaeat.order.domain;

import com.mboaeat.common.PeriodicalElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("NON_STRUCTURED")
public class NonStructuredMenu extends Menu {

}
