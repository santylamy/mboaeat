package com.mboaeat.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("STRUCTURED")
public class CompoungMenu extends Menu {

    @Embedded
    private ImageCollection imageCollection = new ImageCollection();

    @Embedded
    private MenuPriceCollection menuPriceCollection = new MenuPriceCollection();

    @Embedded
    private MenuStatusLinkCollection menuStatusLinkCollection = new MenuStatusLinkCollection();

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "nameFr", column = @Column(name = "MENU_NAME_FR")),
                    @AttributeOverride(name = "nameEn", column = @Column(name = "MENU_NAME_EN"))
            }
    )
    private Name name;

    @Embedded
    private IngredientCollection ingredientCollection = new IngredientCollection();

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "descFr", column = @Column(name = "MENU_DESC_FR")),
                    @AttributeOverride(name = "descEn", column = @Column(name = "MENU_DESC_EN"))
            }
    )
    private Description description;

    @Builder
    public CompoungMenu(Name name, MenuPrice menuPrice, MenuStatusLink menuStatusLink, List<Product> products){
        this.name = name;
        addPrice(menuPrice);
        addMenuStatus(menuStatusLink);
        addProducts(products);
    }

    public MenuPrice getCurrentPrice(){
        return menuPriceCollection.getCurrent();
    }

    public MenuStatusLink getCurrentStatus(){
        return menuStatusLinkCollection.getCurrent();
    }

    public List<MenuPrice> applyChangeMenuPriceCollectionCommand(ChangeMenuPriceCollectionCommand command, boolean commit){
        return menuPriceCollection.apply(command, commit);
    }

    public List<MenuStatusLink> applyStatusChangeLinkCollectionCommand(ChangeMenuStatusLinkCollectionCommand command, boolean commit){
        return menuStatusLinkCollection.apply(command, commit);
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

    public void addPrice(MenuPrice menuPrice){
        if (menuPrice != null) {
            menuPrice.linkMenu(this);
            this.menuPriceCollection.add(menuPrice);
        }
    }

    public void addMenuStatus(MenuStatusLink menuStatusLink){
        if (menuStatusLink != null) {
            menuStatusLink.linkMenu(this);
            this.menuStatusLinkCollection.add(menuStatusLink);
        }
    }
}
