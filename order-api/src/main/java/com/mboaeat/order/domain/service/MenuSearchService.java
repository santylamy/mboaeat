package com.mboaeat.order.domain.service;

import com.mboaeat.common.dto.search.MenuSearchResult;
import com.mboaeat.domain.TranslatableString;
import com.mboaeat.order.controller.menu.MenuConverter;
import com.mboaeat.order.domain.Menu;
import com.mboaeat.order.domain.menu.CompoungMenu;
import com.mboaeat.order.domain.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class MenuSearchService {

    private final MenuRepository menuRepository;

    public Page<MenuSearchResult> findMenus(
                 @NotNull final Pageable pageable,
                 @NotNull final String language,
                 @NotNull final String name
            ){
        ExampleMatcher matcher = ExampleMatcher
                .matchingAny()
                .withMatcher("name.french", contains().ignoreCase())
                .withMatcher("name.english", contains().ignoreCase());
        CompoungMenu menuSearch = CompoungMenu
                .builder()
                .name(TranslatableString.builder().french(name).english(name).build())
                .build();
        Page<Menu> menuPage = menuRepository.findAll(Example.of(menuSearch, matcher), pageable);

        if (!menuPage.isEmpty()){
            return menuPage.map(menu -> MenuConverter.modelsToMenuSearchResult(menu, language));
        }else {
            return Page.empty(pageable);
        }
    }
}
