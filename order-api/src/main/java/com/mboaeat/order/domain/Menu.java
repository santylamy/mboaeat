package com.mboaeat.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "MENUS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "MENU_TYPE")
@AttributeOverride(
        name = "id",
        column = @Column(name = "MENU_ID")
)
public abstract class Menu extends BaseEntity<Long> {

}
