package com.mboaeat.order.domain.menu;

import com.mboaeat.common.dto.StorageProvider;
import com.mboaeat.order.domain.Menu;
import lombok.Builder;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Optional;

import static com.mboaeat.common.dto.StorageProvider.GOOGLE;

@Data
@Entity
@DiscriminatorValue(value = "PHOTO")
public class MenuPhoto extends MenuFile{

    @Builder
    public MenuPhoto(String referenceCloud, StorageProvider provider) {
        super(referenceCloud, Optional.ofNullable(provider).orElse(GOOGLE));
    }

    @Override
    public void setMenu(Menu menu){
        if (getMenu() != null){
            ((CompoungMenu)getMenu()).internalRemovePhoto(this);
        }
        if (menu != null) {
            ((CompoungMenu) menu).internalAddPhoto(this);
        }
    }
}
