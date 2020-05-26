package com.mboaeat.order.domain;

import javax.persistence.*;
import java.util.List;

import static com.mboaeat.common.CollectionsUtils.newArrayList;

@Embeddable
public class IngredientCollection {

    @ElementCollection
    @CollectionTable(name = "MENU_INGREDIENTS", joinColumns = @JoinColumn(name = "MENU_ID"))
    @OrderColumn(name = "INGREDIENT_KEY")
    private List<Ingredient> ingredients= newArrayList();

    protected void add(List<Ingredient> ingredients){
        this.ingredients.addAll(ingredients);
    }

    protected void add(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }

    protected void remove(int key){
        ingredients.removeIf(ingredient -> ingredient.getKey() == key);
    }
}
