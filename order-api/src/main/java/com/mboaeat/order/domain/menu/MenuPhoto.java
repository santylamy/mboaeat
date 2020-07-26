package com.mboaeat.order.domain.menu;

import com.mboaeat.order.domain.Menu;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "PHOTO")
public class MenuPhoto extends MenuFile{

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
