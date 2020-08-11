package com.mboaeat.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MENUS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "MENU_TYPE", discriminatorType = DiscriminatorType.STRING)
@AttributeOverride(
        name = "id",
        column = @Column(name = "MENU_ID")
)
@DiscriminatorOptions(force = true)
public abstract class Menu extends BaseEntity<Long>  {

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "MENU_PRICE")
    )
    @Builder.Default
    private Amount price = Amount.zero();

}
