package com.mboaeat.order.domain.service;

import com.mboaeat.order.domain.*;
import com.mboaeat.order.domain.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final ProductService productService;

    public MenuService(MenuRepository menuRepository, ProductService productService) {
        this.menuRepository = menuRepository;
        this.productService = productService;
    }


    @Transactional
    public Menu createMenu(Menu menu, Product... products){
        menu.addProducts(products);
        return menuRepository.save(menu);
    }


    @Transactional
    public Menu createMenu(Menu menu){
        return menuRepository.save(menu);
    }

    @Transactional
    public void changeMenuPriceToMenu(Long menuId, MenuPrice menuPrice){
        getMenu(menuId).ifPresent(
                menu -> {
                    if (menu instanceof CompoungMenu){
                        ((CompoungMenu) menu).applyChangeMenuPriceCollectionCommand(
                                ChangeMenuPriceCollectionCommand
                                        .builder()
                                        .period(menuPrice.getPeriod())
                                        .menu(menu)
                                        .amount(menuPrice.getAmount())
                                        .build(), true
                        );
                        menuRepository.save(menu);
                    }
                }
        );
    }

    @Transactional
    public void linkProductToMenu(Long menuId, Long productId){
        final Product product = productService.getProduct(productId).get();
        final Menu menu = menuRepository.getOne(menuId);
        menu.addProduct(product);
        menuRepository.save(menu);
    }

    @Transactional
    public void updateMenu(Long menuId, Name name, Description description){
        getMenu(menuId).ifPresent(
                menuSaved -> {
                    if (menuSaved instanceof CompoungMenu){
                        ((CompoungMenu)menuSaved).setName( name );
                        ((CompoungMenu)menuSaved).setDescription( description );
                    }
                    menuRepository.save(menuSaved);
                }
        );
    }

    @Transactional
    public void changeMenuStatusToMenu(Long menuId, MenuStatusLink menuStatusLink){

        getMenu(menuId).ifPresent(
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
                }
        );
    }

    public void addIngredients(Long menuId, Ingredient... ingredient){
        getMenu(menuId).ifPresent(
                menu -> {
                    if (menu instanceof CompoungMenu) {
                        ((CompoungMenu) menu).addIngredient(Arrays.asList(ingredient));
                    }
                }
        );
    }


    public Optional<Menu> getMenu(Long menuId){
        return menuRepository.findById(menuId);
    }

}
