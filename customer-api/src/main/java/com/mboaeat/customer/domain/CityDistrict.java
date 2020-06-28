package com.mboaeat.customer.domain;

import com.mboaeat.domain.TranslatableString;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "CITIES_DISTRICTS")
public class CityDistrict implements Deliverable, Serializable {

    @Id
    @Column(name = "DISTRICT_CODE", insertable = false, updatable = false)
    private String districtCode;

    @ManyToOne
    @JoinColumn(name = "CITY_NIS_CODE")
    private City city;

    @Column(name = "DELIVERABLE")
    private Boolean homeDeliverable;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "french", column = @Column(name = "DISTRICT_DESC_FR")),
            @AttributeOverride(name = "english", column = @Column(name = "DISTRICT_DESC_EN"))
    })
    private TranslatableString districtDescription;

    @Override
    public boolean isDeliverable() {
        return homeDeliverable == null || homeDeliverable;
    }
}
