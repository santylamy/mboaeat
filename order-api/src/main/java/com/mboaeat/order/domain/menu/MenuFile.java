package com.mboaeat.order.domain.menu;

import com.mboaeat.common.dto.StorageProvider;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class MenuFile extends MenuInfo{

    @Column(name = "FILE_ID_IN_CLOUD")
    private String referenceCloud;

    @Column(name = "FILE_PROVIDER")
    @Enumerated(EnumType.STRING)
    private StorageProvider provider;
}
