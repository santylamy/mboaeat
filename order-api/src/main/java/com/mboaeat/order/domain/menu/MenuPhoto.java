package com.mboaeat.order.domain.menu;

import com.mboaeat.order.domain.Menu;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue(value = "PHOTO")
public class MenuPhoto extends MenuFile{

    public void setMenu(Menu menu){
        if (getMenu() != null){
            ((CompoungMenu)getMenu()).internalRemovePhoto(this);
        }
        setMenu(menu);
        if (menu != null) {
            ((CompoungMenu) menu).internalAddPhoto(this);
        }
    }
}
