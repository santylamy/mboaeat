package com.mboaeat.order.domain.menu;

import com.mboaeat.domain.CollectionsUtils;
import com.mboaeat.order.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static com.mboaeat.domain.CollectionsUtils.newArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class IngredientCollection implements Serializable {

    @ElementCollection
    @CollectionTable(name = "MENU_INGREDIENTS", joinColumns = @JoinColumn(name = "MENU_ID"))
    @OrderColumn(name = "INGREDIENT_KEY")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Ingredient> ingredients= newArrayList();

    protected void add(List<Ingredient> ingredients, Menu menu){
        if (!CollectionsUtils.isEmpty(ingredients)) {
            ingredients
                    .stream()
                    .forEach(
                            ingredient -> add(ingredient, menu)
                    );
        }
    }

    protected void add(Ingredient ingredient, Menu menu){
        ingredient.linkMenu(menu);
        this.ingredients.add(ingredient);
    }

    protected void remove(int key){
        ingredients.removeIf(ingredient -> ingredient.getKey() == key);
    }
}
