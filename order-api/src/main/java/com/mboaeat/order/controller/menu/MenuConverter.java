package com.mboaeat.order.controller.menu;

import com.mboaeat.common.dto.search.MenuSearchResult;
import com.mboaeat.common.translation.Language;
import com.mboaeat.domain.Translatable;
import com.mboaeat.domain.TranslatableString;
import com.mboaeat.order.domain.*;
import com.mboaeat.order.domain.menu.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.mboaeat.domain.CollectionsUtils.newArrayList;

public class MenuConverter {

    public static SimpleMenuRequest menuToSimpleMenuModel(CompoungMenu menu, String langCode){
        Language language = Language.toLanguage(langCode);
        return menuToSimpleMenuModel(menu, language);
    }

    public static SimpleMenuRequest menuToSimpleMenuModel(CompoungMenu menu, Language language){
        SimpleMenuRequest.SimpleMenuRequestBuilder menuModelBuilder = SimpleMenuRequest.builder();
        menuModelBuilder.id(menu.getId())
                .category(menu.getCategory() != null ? translateFieldMenuByLanguage(menu.getCategory().getDescription(), language) : null)
                .description(menu.getDescription() != null ? translateFieldMenuByLanguage(menu.getDescription(), language) : null)
                .name(translateFieldMenuByLanguage(menu.getName(), language))
                .nutritional(menu.getNutritional() != null ? translateFieldMenuByLanguage(menu.getNutritional(), language) : null)
                .preparationTip(menu.getPreparationTip() != null ? translateFieldMenuByLanguage(menu.getPreparationTip(), language) : null)
                .priceOption(menuPriceOptionsToModels(menu.currentMenuPriceOptions()))
                .ingredient(menu.getIngredientCollection().isEmpty() ? translateFieldMenuByLanguage(menu.getIngredientCollection().getIngredients().stream().findFirst().get().getName(), language) : null);
       return menuModelBuilder.build();
    }

    public static String translateFieldMenuByLanguage(Translatable translatable, Language language){
        return translatable.asString(language);
    }

    public static Menu modelToMenu(MenuRequest menuRequest) {
        CompoungMenu.CompoungMenuBuilder menu = CompoungMenu.builder();
        menu.ingredients(List.of(modelToIngredient(menuRequest.getIngredient())))
                .menuPrice(modelToMenuPrice(menuRequest.getPrice()))
                .menuStatusLink(modelToMenuStatusLink(menuRequest.getOnSale()))
                .name(
                        TranslatableString
                                .builder()
                                .french(menuRequest.getName().getMenuNameFr())
                                .english(menuRequest.getName().getMenuNameEn())
                                .build())
                .nutritional(modelToMenuNutritional(menuRequest.getNutritional()))
                .preparationTip(modelToMenuPreparationTrip(menuRequest.getPreparationTip()))
                .priceOptions(modelToMenuPriceOptions(menuRequest.getPriceOption()))
                .description(modelToMenuDescription(menuRequest.getDescription()));

        return menu.build();
    }

    public static MenuRequest menuToModel(CompoungMenu menu){
        return MenuRequest
                .builder()
                .reference(menu.getId())
                .category(menu.getCategory() != null ? menu.getCategory().getCode() : null)
                .description(menuDescriptionToModel(menu.getDescription()))
                .name(menuNameToModel(menu.getName()))
                .nutritional(menu.getNutritional() != null ? menuNutritionalToModel(menu.getNutritional()) : MenuNutritional.builder().build())
                .preparationTip(menu.getPreparationTip() != null ? menuPreparationTipToModel(menu.getPreparationTip()) : MenuPreparationTip.builder().build())
                .onSale(menu.getCurrentStatus() != null ? menuStatusLinkToModel(menu.getCurrentStatus()) : MenuOnSale.builder().build())
                .price(menu.getCurrentPrice() != null ? menuPriceToModel(menu.getCurrentPrice()) : MenuCost.builder().build())
                .priceOption(menu.currentMenuPriceOptions() != null ? menuPriceOptionsToModels(menu.currentMenuPriceOptions()) : newArrayList())
                .ingredient(menuIngredientsToModels(menu.getIngredientCollection().getIngredients()))
                .build();
    }

    public static MenuIngredient menuIngredientsToModels(List<Ingredient> ingredients) {
        return ingredients.stream().map(MenuConverter::menuIngredientToModel).findFirst().orElse(MenuIngredient.builder().build());
    }

    public static MenuIngredient menuIngredientToModel(Ingredient ingredient) {
        return MenuIngredient
                .builder()
                .id(Objects.toString(ingredient.getKey()))
                .ingredientsEn(ingredient.getName().getEnglish())
                .ingredientsFr(ingredient.getName().getFrench())
                .build();
    }

    public static List<MenuCostOption> menuPriceOptionsToModels(List<MenuPriceOption> currentMenuPriceOptions) {
        if (currentMenuPriceOptions == null)
            return newArrayList();
        return currentMenuPriceOptions.stream().map(MenuConverter::menuPriceOptionToModel).collect(Collectors.toList());
    }

    public static MenuCostOption menuPriceOptionToModel(MenuPriceOption menuPriceOption){
        return MenuCostOption
                .builder()
                .id(Objects.toString(menuPriceOption.getId()))
                .price(menuPriceOption.getAmount().getValue())
                .quantity(menuPriceOption.getQuantity())
                .build();
    }

    public static MenuCost menuPriceToModel(MenuPrice currentPrice) {
        return MenuCost
                .builder()
                .amount(currentPrice.getAmount().getValue().doubleValue())
                .start(currentPrice.getPeriod().getStartDate())
                .end(currentPrice.getPeriod().getEndDate())
                .id(Objects.toString(currentPrice.getId()))
                .build();
    }

    public static MenuOnSale menuStatusLinkToModel(MenuStatusLink currentStatus) {
        return MenuOnSale
                .builder()
                .start(currentStatus.getPeriod().getStartDate())
                .end(currentStatus.getPeriod().getEndDate())
                .status(currentStatus.getMenuStatus())
                .id(Objects.toString(currentStatus.getId()))
                .build();
    }

    public static MenuPreparationTip menuPreparationTipToModel(TranslatableString preparationTip) {
        if (preparationTip != null){
            return MenuPreparationTip
                    .builder()
                    .preparationEn(preparationTip.getEnglish())
                    .preparationFr(preparationTip.getFrench())
                    .build();
        }
        return null;
    }

    public static MenuNutritional menuNutritionalToModel(TranslatableString nutritional) {
        if (nutritional != null){
            return MenuNutritional
                    .builder()
                    .menuNutritionalEn(nutritional.getEnglish())
                    .menuNutritionalFr(nutritional.getFrench())
                    .build();
        }
        return null;
    }


    public static MenuName menuNameToModel(TranslatableString name) {
        return MenuName
                .builder()
                .menuNameEn(name.getEnglish())
                .menuNameFr(name.getFrench())
                .build();
    }

    public static MenuDescription menuDescriptionToModel(TranslatableString description) {
        if (description != null){
            return MenuDescription
                    .builder()
                    .descriptionEn(description.getEnglish())
                    .descriptionFr(description.getFrench())
                    .build();
        }
        return null;
    }

    public static TranslatableString modelToMenuDescription(MenuDescription description) {
        if (description != null){
            return TranslatableString
                    .builder()
                    .english(description.getDescriptionEn())
                    .french(description.getDescriptionFr())
                    .build();
        }
        return null;
    }

    public static List<MenuPriceOption> modelToMenuPriceOptions(List<MenuCostOption> priceOption) {
        return priceOption
                .stream()
                .map(MenuConverter::modelToMenuPriceOption)
                .collect(Collectors.toList());
    }

    public static MenuPriceOption modelToMenuPriceOption(MenuCostOption menuCostOption){
        return MenuPriceOption
                .builder()
                .quantity(menuCostOption.getQuantity())
                .amount(Amount.builder().value(menuCostOption.getPrice()).build())
                .build();
    }

    public static TranslatableString modelToMenuPreparationTrip(MenuPreparationTip preparationTip) {
        if (preparationTip != null){
            return TranslatableString
                    .builder()
                    .english(preparationTip.getPreparationEn())
                    .french(preparationTip.getPreparationFr())
                    .build();
        }
        return null;
    }

    public static TranslatableString modelToMenuNutritional(MenuNutritional nutritional) {
        if (nutritional != null){
            return TranslatableString
                    .builder()
                    .french(nutritional.getMenuNutritionalFr())
                    .english(nutritional.getMenuNutritionalEn())
                    .build();
        }
        return null;
    }

    public static MenuStatusLink modelToMenuStatusLink(MenuOnSale onSale) {
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

    public static MenuPrice modelToMenuPrice(MenuCost menuCost){
        if (menuCost instanceof MenuCostExtended){
            return MenuPrice
                    .builder()
                    .priceOptionCollection(MenuPriceOptionCollection.builder().priceOptions(modelToMenuPriceOptions(((MenuCostExtended) menuCost).getCostOptions())).build())
                    .period(PeriodByDay.builder().startDate(menuCost.getStart()).endDate(menuCost.getEnd()).build())
                    .amount(Amount.ValueOf(menuCost.getAmount()))
                    .build();
        }
        return MenuPrice
                .builder()
                .period(PeriodByDay.builder().startDate(menuCost.getStart()).endDate(menuCost.getEnd()).build())
                .amount(Amount.ValueOf(menuCost.getAmount()))
                .build();
    }

    public static Ingredient modelToIngredient(MenuIngredient menuIngredient){
        Ingredient ingredient = Ingredient
                .builder()
                .key(Integer.valueOf(menuIngredient.getId()))
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

    public static MenuSearchResult modelsToMenuSearchResult(Menu menu, String lang){
        Language language = Language.toLanguage(lang);
        return MenuSearchResult
                .builder()
                .id(menu.getId())
                .category( ((CompoungMenu)menu).getCategory() != null ? ((CompoungMenu)menu).getCategory().getDescription().asString(language) : null)
                .name(((CompoungMenu) menu).getName().asString(language))
                .price(((CompoungMenu) menu).getCurrentPriceAsDouble())
                .build();
    }
}
