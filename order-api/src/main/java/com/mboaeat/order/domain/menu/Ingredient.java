package com.mboaeat.order.domain.menu;

import com.mboaeat.order.domain.Menu;
import com.mboaeat.domain.TranslatableString;
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
                    @AttributeOverride(name = "french", column = @Column(name = "INGREDIENT_NAME_FR")),
                    @AttributeOverride(name = "english", column = @Column(name = "INGREDIENT_NAME_EN"))
            }
    )
    private TranslatableString name;

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
