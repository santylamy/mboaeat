package com.mboaeat.order.domain.menu;

import com.mboaeat.order.domain.BaseEntity;
import com.mboaeat.order.domain.TranslatableString;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "MENU_CATEGORY")
public class MenuCategory extends BaseEntity<Long> {

    @Embedded
    private TranslatableString description = new TranslatableString();

    private String code;

}
