package com.mboaeat.order.domain.menu;

import com.mboaeat.common.dto.StorageProvider;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import static com.mboaeat.common.dto.StorageProvider.GOOGLE;

@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public class MenuFile extends MenuInfo{

    @Column(name = "FILE_ID_IN_CLOUD")
    private String referenceCloud;

    @Column(name = "FILE_PROVIDER")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StorageProvider provider = GOOGLE;
}
