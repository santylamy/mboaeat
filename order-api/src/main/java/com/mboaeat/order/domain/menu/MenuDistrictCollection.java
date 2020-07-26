package com.mboaeat.order.domain.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

import static com.mboaeat.common.dto.DataStatus.ACCEPTED;
import static com.mboaeat.domain.CollectionsUtils.newArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MenuDistrictCollection implements Serializable {

    @Builder.Default
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuDistrict> districts = newArrayList();

    public boolean existDistrict(String districtCode){
        return this.districts
                .stream()
                .filter(menuDistrict -> menuDistrict.getStatus() == ACCEPTED)
                .anyMatch(menuDistrict -> menuDistrict.getDistrictNisCode().equalsIgnoreCase(districtCode));
    }

}
