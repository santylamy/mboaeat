package com.mboaeat.order.domain.service;

import com.mboaeat.common.exception.ResourceNotFoundException;
import com.mboaeat.domain.TranslatableString;
import com.mboaeat.order.domain.*;
import com.mboaeat.order.domain.menu.*;
import com.mboaeat.order.domain.product.Product;
import com.mboaeat.order.domain.repository.MenuCategoryRepository;
import com.mboaeat.order.domain.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

import static com.mboaeat.domain.CollectionsUtils.getLast;

@Service
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final ProductService productService;

    public MenuService(MenuRepository menuRepository, MenuCategoryRepository menuCategoryRepository, ProductService productService) {
        this.menuRepository = menuRepository;
        this.menuCategoryRepository = menuCategoryRepository;
        this.productService = productService;
    }


    @Transactional
    public Menu createMenu(Menu menu, Product... products){
        if (menu instanceof NonStructuredMenu) {
            ((NonStructuredMenu) menu).addProducts(products);
        }
        return createMenu(menu);
    }

    @Transactional
    public Menu createMenu(CompoungMenu menu, String category){
        MenuCategory menuCategory = menuCategoryRepository.getByCode(category);
        menu.category(menuCategory);
        return createMenu(menu);
    }

    @Transactional
    public Menu createMenu(Menu menu){
        return menuRepository.save(menu);
    }

    @Transactional
    public void changeMenuPriceToMenu(Long menuId, MenuPrice menuPrice){
        findByMenuId(menuId).ifPresentOrElse(
                menu -> {
                    if (menu instanceof CompoungMenu){
                        ((CompoungMenu) menu).applyChangeMenuPriceCollectionCommand(
                                ChangeMenuPriceCollectionCommand
                                        .builder()
                                        .period(menuPrice.getPeriod())
                                        .priceOptions(menuPrice.priceColPriceOptions())
                                        .menu(menu)
                                        .amount(menuPrice.getAmount())
                                        .build(), true
                        );
                        menuRepository.save(menu);
                    }
                }, () -> {throw new ResourceNotFoundException();}
        );
    }

    @Transactional
    public void linkProductToMenu(Long menuId, Long productId){
        final Product product = productService.getProduct(productId).get();
        final Menu menu = menuRepository.getOne(menuId);
        if (menu instanceof NonStructuredMenu) {
            ((NonStructuredMenu) menu).addProducts(product);
        }
        menuRepository.save(menu);
    }

    @Transactional
    public void updateMenu(Long menuId, TranslatableString name, TranslatableString nutritional, TranslatableString preparationTip, TranslatableString description){
        findByMenuId(menuId).ifPresentOrElse(
                menuSaved -> {
                    if (menuSaved instanceof CompoungMenu){
                        if (((CompoungMenu) menuSaved).getName().hasChange(name)) {
                            ((CompoungMenu) menuSaved).setName(name);
                        }
                        if ( ((CompoungMenu) menuSaved).getDescription() == null || ((CompoungMenu) menuSaved).getDescription().hasChange(description)) {
                            ((CompoungMenu) menuSaved).setDescription(description);
                        }
                        if ( ((CompoungMenu) menuSaved).getNutritional() == null || ((CompoungMenu) menuSaved).getNutritional().hasChange(nutritional)){
                            ((CompoungMenu) menuSaved).setNutritional(nutritional);
                        }
                        if ( ((CompoungMenu) menuSaved).getPreparationTip() == null || ((CompoungMenu) menuSaved).getPreparationTip().hasChange(preparationTip)) {
                            ((CompoungMenu) menuSaved).setPreparationTip(preparationTip);
                        }
                    }
                    menuRepository.save(menuSaved);
                }, () -> {throw new ResourceNotFoundException();}
        );
    }

    @Transactional
    public void changeMenuStatusToMenu(Long menuId, MenuStatusLink menuStatusLink){

        findByMenuId(menuId).ifPresentOrElse(
                menu -> {
                    if (menu instanceof CompoungMenu){
                        CompoungMenu savedMenu = (CompoungMenu) menu;
                        savedMenu.applyStatusChangeLinkCollectionCommand(
                                ChangeMenuStatusLinkCollectionCommand
                                        .builder()
                                        .menuStatus(menuStatusLink.getMenuStatus())
                                        .period(menuStatusLink.getPeriod())
                                        .menu(savedMenu)
                                        .build(),
                                true
                        );
                    }
                    menuRepository.save(menu);
                }, () -> {throw new ResourceNotFoundException();}
        );
    }

    @Transactional
    public void addIngredients(Long menuId, Ingredient... ingredient){
        findByMenuId(menuId).ifPresentOrElse(
                menu -> {
                    if (menu instanceof CompoungMenu) {
                        ((CompoungMenu) menu).addIngredient(Arrays.asList(ingredient));
                    }
                    menuRepository.save(menu);
                }, () -> {throw new ResourceNotFoundException();}
        );
    }

    @Transactional
    public void removeIngredients(Long menuId, Ingredient ingredient){
        findByMenuId(menuId).ifPresentOrElse(
                menu -> {
                    if (menu instanceof CompoungMenu) {
                        ((CompoungMenu) menu).removeIngredient(ingredient);
                    }
                    menuRepository.save(menu);
                }, () -> {throw new ResourceNotFoundException();}
        );
    }


    public Optional<Menu> findByMenuId(Long menuId){
        return menuRepository.findById(menuId);
    }

    public Menu getMenu(Long menuId){
        return findByMenuId(menuId).orElseThrow(() -> new ResourceNotFoundException());
    }

    @Transactional
    public Long createPhoto(final MenuPhoto photo, Long menuId){
        CompoungMenu menu = (CompoungMenu) getMenu(menuId);
        menu.addPhoto(photo);
        menuRepository.save(menu);
        return getLast(menu.getMenuPhotoCollection().getPhotos()).getId();
    }

    @Transactional
    public void updateMenu(Long menuId, CompoungMenu menu) {
        updateMenu(menuId, menu.getName(), menu.getNutritional(), menu.getPreparationTip(), menu.getDescription());
    }
}
