package com.mboaeat.order.domain.menu;

import com.mboaeat.domain.CollectionsUtils;
import com.mboaeat.domain.TranslatableString;
import com.mboaeat.order.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.mboaeat.domain.AbstractPeriodicalCollection.assertPeriodicalsNotNull;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("STRUCTURED")
public class CompoungMenu extends Menu {

    @Embedded
    private MenuPhotoCollection menuPhotoCollection = new MenuPhotoCollection();

    @Embedded
    private MenuPriceCollection menuPriceCollection = new MenuPriceCollection();

    @Embedded
    private MenuStatusLinkCollection menuStatusLinkCollection = new MenuStatusLinkCollection();

    @Embedded
    private MenuDistrictCollection menuDistrictCollection = new MenuDistrictCollection();

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "french", column = @Column(name = "MENU_NAME_FR")),
                    @AttributeOverride(name = "english", column = @Column(name = "MENU_NAME_EN"))
            }
    )
    private TranslatableString name = new TranslatableString();

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "french", column = @Column(name = "MENU_NUTRITIONAL_FR")),
                    @AttributeOverride(name = "english", column = @Column(name = "MENU_NUTRITIONAL_EN"))
            }
    )
    private TranslatableString nutritional = new TranslatableString();

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "french", column = @Column(name = "MENU_PREPARATION_TIP_FR")),
                    @AttributeOverride(name = "english", column = @Column(name = "MENU_PREPARATION_TIP_EN"))
            }
    )
    private TranslatableString preparationTip = new TranslatableString();

    @Embedded
    private IngredientCollection ingredientCollection = new IngredientCollection();

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "french", column = @Column(name = "MENU_DESC_FR")),
                    @AttributeOverride(name = "english", column = @Column(name = "MENU_DESC_EN"))
            }
    )
    private TranslatableString description = new TranslatableString();

    @ManyToOne
    @JoinColumn(name = "MENU_CATEGORY_ID")
    private MenuCategory category;

    @Builder
    public CompoungMenu(TranslatableString name, TranslatableString nutritional,
                        TranslatableString preparationTip, MenuPrice menuPrice,
                        MenuStatusLink menuStatusLink, List<Ingredient> ingredients,
                        List<MenuPriceOption> priceOptions, TranslatableString description){
        this.name = name;
        this.nutritional = nutritional;
        this.preparationTip = preparationTip;
        this.description = description;
        addIngredient(ingredients);
        addPrice(menuPrice);
        addMenuStatus(menuStatusLink);
        addPriceOption(priceOptions);
    }

    public MenuPrice getCurrentPrice(){
        return menuPriceCollection.getCurrent();
    }

    public Double getCurrentPriceAsDouble(){
        if (getCurrentPrice() != null)
          return getCurrentPrice().getAmount().getValue().doubleValue();
        return null;
    }

    public List<MenuPriceOption> currentMenuPriceOptions(){
        if (getCurrentPrice() != null)
            return getCurrentPrice().getPriceOptionCollection().getPriceOptions();
        return new ArrayList<>();
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
        this.ingredientCollection.add(ingredients, this);
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredientCollection.add(ingredient, this);
    }

    public void removeIngredient(Ingredient ingredient){
        removeIngredient(ingredient.getKey());
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

    private void addPriceOption(List<MenuPriceOption> priceOptions) {
        if (!CollectionsUtils.isEmpty(priceOptions)){
            assertPeriodicalsNotNull(menuPriceCollection.getPeriodicals());
            this.menuPriceCollection.addPriceOption(priceOptions, this);
        }
    }

    public void category(MenuCategory menuCategory) {
        this.category = category;
    }

    public void addPhoto(MenuPhoto photo){
        photo.setMenu(this);
    }

    protected void internalRemovePhoto(MenuPhoto photo){
        getMenuPhotoCollection().getPhotos().remove(photo);
    }

    protected void internalAddPhoto(MenuPhoto photo){
        getMenuPhotoCollection().getPhotos().add(photo);
    }

    public void addDistrict(MenuDistrict menuDistrict){
        menuDistrict.setMenu(this);
    }

    public void removeDistrict(MenuDistrict menuDistrict){
        internalRemoveDistrict(menuDistrict);
    }

    protected void internalAddDistrict(MenuDistrict menuDistrict) {
        getMenuDistrictCollection().getDistricts().add(menuDistrict);
    }

    protected void internalRemoveDistrict(MenuDistrict menuDistrict) {
        getMenuDistrictCollection().getDistricts().remove(menuDistrict);
    }

    public void updateIngredient(Ingredient ingredientToUpdate) {
        if (ingredientToUpdate == null){
            return;
        }
        Ingredient ingredient = getIngredientCollection().ingredient(ingredientToUpdate);
        if (ingredient == null){
            return;
        }
        ingredient.setName(ingredientToUpdate.getName());
    }
}
