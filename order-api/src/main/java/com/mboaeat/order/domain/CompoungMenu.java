package com.mboaeat.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("STRUCTURED")
public class CompoungMenu extends Menu {

    @Embedded
    private ImageCollection imageCollection = new ImageCollection();

    @Embedded
    private MenuPriceCollection menuPriceCollection = new MenuPriceCollection();

    @Embedded
    private MenuStatusLinkCollection menuStatusLinkCollection = new MenuStatusLinkCollection();

    @Embedded
    private MenuName name;

    @Embedded
    private IngredientCollection ingredientCollection = new IngredientCollection();

    @Column(name = "MENU_DESC")
    private String description;

    public MenuPrice getCurrentPrice(){
        return menuPriceCollection.getCurrent();
    }

    public List<MenuPrice> apply(ChangeMenuPriceCollectionCommand command, boolean commit){
        return menuPriceCollection.apply(command, true);
    }

    public List<MenuStatusLink> apply(ChangeMenuStatusLinkCollectionCommand command, boolean commit){
        return menuStatusLinkCollection.apply(command, true);
    }

    public void addIngredient(List<Ingredient> ingredients) {
        this.ingredientCollection.add(ingredients);
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredientCollection.add(ingredient);
    }

    public void removeIngredient(int key){
        this.ingredientCollection.remove(key);
    }
}
