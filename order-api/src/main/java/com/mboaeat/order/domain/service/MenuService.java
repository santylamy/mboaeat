package com.mboaeat.order.domain.service;

import com.mboaeat.common.dto.request.DistrictRequest;
import com.mboaeat.common.dto.menu.request.ImageRequest;
import com.mboaeat.common.exception.ResourceNotFoundException;
import com.mboaeat.domain.TranslatableString;
import com.mboaeat.order.domain.*;
import com.mboaeat.order.domain.menu.*;
import com.mboaeat.order.domain.product.Product;
import com.mboaeat.order.domain.repository.MenuCategoryRepository;
import com.mboaeat.order.domain.repository.MenuDistrictRepository;
import com.mboaeat.order.domain.repository.MenuPhotoRepository;
import com.mboaeat.order.domain.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

import static com.mboaeat.domain.CollectionsUtils.getLast;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final ProductService productService;
    private final MenuPhotoRepository menuPhotoRepository;
    private final MenuDistrictRepository menuDistrictRepository;

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
    public void updateMenu(Long menuId, final CompoungMenu menu) {
        updateMenu(menuId, menu.getName(), menu.getNutritional(), menu.getPreparationTip(), menu.getDescription());
    }


    @Transactional
    public void createPhoto(final ImageRequest photo, final CompoungMenu menu){
        MenuPhoto menuPhoto = MenuPhoto.builder().referenceCloud(photo.getIdInCLoud()).build();
        menu.addPhoto(menuPhoto);
        menuRepository.save(menu);
    }

    @Transactional
    public void updatePhoto(final ImageRequest photo, String referenceCloud, final CompoungMenu menu){
        MenuPhoto menuPhoto = menuPhotoRepository.findByReferenceCloud(referenceCloud);
        menuPhoto.setReferenceCloud(photo.getIdInCLoud());
        menuPhotoRepository.save(menuPhoto);
    }

    @Transactional
    public void createDistrict(final DistrictRequest district, final CompoungMenu menu){
       if (menu.getMenuDistrictCollection().existDistrict(district.getCode()))
           return;
       MenuDistrict menuDistrict = MenuDistrict.builder().districtNisCode(district.getCode()).build();
       menu.addDistrict(menuDistrict);
       menuRepository.save(menu);
    }

    @Transactional
    public void removeDistrict(final DistrictRequest district, final CompoungMenu menu){
        MenuDistrict menuDistrict = menuDistrictRepository.findByDistrictNisCode(district.getCode());
        menu.removeDistrict(menuDistrict);
        menuRepository.save(menu);
    }
}
