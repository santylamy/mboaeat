package com.mboaeat.order.controller.menu;

import com.mboaeat.order.domain.Amount;
import com.mboaeat.order.domain.Menu;
import com.mboaeat.order.domain.PeriodByDay;
import com.mboaeat.order.domain.TranslatableString;
import com.mboaeat.order.domain.menu.*;

import java.util.List;
import java.util.stream.Collectors;

public class MenuConverter {

    public static Menu modelToMenu(MenuModel menuModel) {
        CompoungMenu.CompoungMenuBuilder menu = CompoungMenu.builder();
        menu.ingredients(List.of(modelToIngredient(menuModel.getIngredient())))
                .menuPrice(modelToMenuPrice(menuModel.getPrice()))
                .menuStatusLink(modelToMenuStatusLink(menuModel.getOnSale()))
                .name(
                        TranslatableString
                                .builder()
                                .french(menuModel.getName().getMenuNameFr())
                                .english(menuModel.getName().getMenuNameEn())
                                .build())
                .nutritional(modelToMenuNutritional(menuModel.getNutritional()))
                .preparationTip(modelToMenuPreparationTrip(menuModel.getPreparationTip()))
                .priceOptions(modelToMenuPriceOptions(menuModel.getPriceOption()))
                .description(modelToMenuDescription(menuModel.getDescription()));

        return menu.build();
    }

    public MenuModel MenuToModel(CompoungMenu menu){
        return MenuModel
                .builder()
                .id(menu.getId())
                .category(menu.getCategory().getCode())
                .description(menuDescriptionToModel(menu.getDescription()))
                .name(menuNameToModel(menu.getName()))
                .nutritional(menuNutritionalToModel(menu.getNutritional()))
                .preparationTip(menuPreparationTipToModel(menu.getPreparationTip()))
                .onSale(menuStatusLinkToModel(menu.getCurrentStatus()))
                .price(menuPriceToModel(menu.getCurrentPrice()))
                .priceOption(menuPriceOptionsToModels(menu.currentMenuPriceOptions()))
                .ingredient(menuIngredientsToModels(menu.getIngredientCollection().getIngredients()))
                .build();
    }

    private MenuIngredient menuIngredientsToModels(List<Ingredient> ingredients) {
        return ingredients.stream().map(MenuConverter::menuIngredientToModel).findFirst().get();
    }

    private static MenuIngredient menuIngredientToModel(Ingredient ingredient) {
        return MenuIngredient
                .builder()
                .ingredientsEn(ingredient.getName().getEnglish())
                .ingredientsFr(ingredient.getName().getFrench())
                .build();
    }

    private List<MenuCostOption> menuPriceOptionsToModels(List<MenuPriceOption> currentMenuPriceOptions) {
        return currentMenuPriceOptions.stream().map(MenuConverter::menuPriceOptionToModel).collect(Collectors.toList());
    }

    private static MenuCostOption menuPriceOptionToModel(MenuPriceOption menuPriceOption){
        return MenuCostOption
                .builder()
                .price(menuPriceOption.getAmount().getValue().doubleValue())
                .quantity(menuPriceOption.getQuantity())
                .build();
    }

    private MenuCost menuPriceToModel(MenuPrice currentPrice) {
        return MenuCost
                .builder()
                .amount(currentPrice.getAmount().getValue().doubleValue())
                .start(currentPrice.getPeriod().getStartDate())
                .end(currentPrice.getPeriod().getEndDate())
                .build();
    }

    private MenuOnSale menuStatusLinkToModel(MenuStatusLink currentStatus) {
        return MenuOnSale
                .builder()
                .start(currentStatus.getPeriod().getStartDate())
                .end(currentStatus.getPeriod().getEndDate())
                .status(currentStatus.getMenuStatus())
                .build();
    }

    private MenuPreparationTip menuPreparationTipToModel(TranslatableString preparationTip) {
        if (preparationTip != null){
            return MenuPreparationTip
                    .builder()
                    .preparationEn(preparationTip.getEnglish())
                    .preparationFr(preparationTip.getFrench())
                    .build();
        }
        return null;
    }

    private MenuNutritional menuNutritionalToModel(TranslatableString nutritional) {
        if (nutritional != null){
            return MenuNutritional
                    .builder()
                    .menuNutritionalEn(nutritional.getEnglish())
                    .menuNutritionalFr(nutritional.getFrench())
                    .build();
        }
        return null;
    }


    private MenuName menuNameToModel(TranslatableString name) {
        return MenuName
                .builder()
                .menuNameEn(name.getEnglish())
                .menuNameFr(name.getFrench())
                .build();
    }

    private MenuDescription menuDescriptionToModel(TranslatableString description) {
        if (description != null){
            return MenuDescription
                    .builder()
                    .descriptionEn(description.getEnglish())
                    .descriptionFr(description.getFrench())
                    .build();
        }
        return null;
    }

    private static TranslatableString modelToMenuDescription(MenuDescription description) {
        if (description != null){
            return TranslatableString
                    .builder()
                    .english(description.getDescriptionEn())
                    .french(description.getDescriptionFr())
                    .build();
        }
        return null;
    }

    private static List<MenuPriceOption> modelToMenuPriceOptions(List<MenuCostOption> priceOption) {
        return priceOption
                .stream()
                .map(MenuConverter::modelToMenuPriceOption)
                .collect(Collectors.toList());
    }

    private static MenuPriceOption modelToMenuPriceOption(MenuCostOption menuCostOption){
        return MenuPriceOption
                .builder()
                .quantity(menuCostOption.getQuantity())
                .amount(Amount.ValueOf(menuCostOption.getPrice()))
                .build();
    }

    private static TranslatableString modelToMenuPreparationTrip(MenuPreparationTip preparationTip) {
        if (preparationTip != null){
            return TranslatableString
                    .builder()
                    .english(preparationTip.getPreparationEn())
                    .french(preparationTip.getPreparationFr())
                    .build();
        }
        return null;
    }

    private static TranslatableString modelToMenuNutritional(MenuNutritional nutritional) {
        if (nutritional != null){
            return TranslatableString
                    .builder()
                    .french(nutritional.getMenuNutritionalFr())
                    .english(nutritional.getMenuNutritionalEn())
                    .build();
        }
        return null;
    }

    private static MenuStatusLink modelToMenuStatusLink(MenuOnSale onSale) {
        if (onSale == null){
            return MenuStatusLink.builder().menuStatus(MenuStatus.Menu_Available).period(PeriodByDay.periodByDayStartingToday()).build();
        }
        return MenuStatusLink
                .builder()
                .menuStatus(onSale.getStatus())
                .period(
                        PeriodByDay
                                .builder()
                                .startDate(onSale.getStart())
                                .endDate(onSale.getEnd()).build()
                )
                .build();
    }

    private static MenuPrice modelToMenuPrice(MenuCost menuCost){
        return MenuPrice
                .builder()
                .amount(Amount.ValueOf(menuCost.getAmount()))
                .build();
    }

    private static Ingredient modelToIngredient(MenuIngredient menuIngredient){
        Ingredient ingredient = Ingredient
                .builder()
                .name(
                        TranslatableString
                                .builder()
                                .french(menuIngredient.getIngredientsFr())
                                .english(menuIngredient.getIngredientsEn())
                                .build()
                )
                .build();
        return ingredient;
    }
}
