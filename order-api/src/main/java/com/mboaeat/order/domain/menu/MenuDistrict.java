package com.mboaeat.order.domain.menu;

import com.mboaeat.order.domain.Menu;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue(value = "DISTRICT")
public class MenuDistrict extends MenuInfo{

    @Column(name = "DISTRICT_NIS_CODE")
    private String districtNisCode;

    @Builder
    public MenuDistrict(String districtNisCode) {
        this.districtNisCode = districtNisCode;
    }

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
