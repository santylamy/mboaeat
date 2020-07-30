package com.mboaeat.order.domain.menu;

import com.mboaeat.common.dto.StorageProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class MenuFile extends MenuInfo{

    @Column(name = "FILE_ID_IN_CLOUD")
    private String referenceCloud;

    @Column(name = "FILE_PROVIDER")
    @Enumerated(EnumType.STRING)
    private StorageProvider provider = StorageProvider.GOOGLE;
}
