package com.mboaeat.order.domain.menu;

import com.mboaeat.order.domain.Menu;
import com.mboaeat.order.domain.Name;
import lombok.*;
import org.hibernate.annotations.Parent;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Ingredient {

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "nameFr", column = @Column(name = "INGREDIENT_NAME_FR")),
                    @AttributeOverride(name = "nameEn", column = @Column(name = "INGREDIENT_NAME_EN"))
            }
    )
    private Name name;

    @Parent
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Menu menu;

    @Column(name = "INGREDIENT_KEY", insertable = false, updatable = false)
    private Integer key;

    public void linkMenu(Menu menu) {
        this.menu = menu;
    }
}
