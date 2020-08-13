package com.mboaeat.order.domain.menu;

import com.mboaeat.domain.TranslatableString;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "MENU_CATEGORY")
public class MenuCategory implements Serializable {

    @Id
    @Column(name = "MENU_CATEGORY_ID")
    private Long id;

    @Embedded
    private TranslatableString description = new TranslatableString();

    private String code;

}
