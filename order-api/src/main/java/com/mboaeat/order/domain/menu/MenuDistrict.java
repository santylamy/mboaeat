package com.mboaeat.order.domain.menu;

import com.mboaeat.order.domain.Menu;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "DISTRICT")
public class MenuDistrict extends MenuInfo{

    @Column(name = "DISTRICT_NIS_CODE")
    private String districtNisCode;

    @Override
    public void setMenu(Menu menu){
        if (getMenu() != null){
            ((CompoungMenu)getMenu()).internalRemoveDistrict(this);
        }
        if (menu != null) {
            ((CompoungMenu) menu).internalAddDistrict(this);
        }
    }

}
