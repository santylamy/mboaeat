package com.mboaeat.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class Image implements Serializable {

    protected static final String ID_GENERATOR = "idGeneratorImages";

    @Column(name = "IMAGE_ID", insertable = false, updatable = false)
    private Long id;

    @Column(name = "SOURCE_ID", insertable = false, updatable = false)
    private Long baseEntity;

    @Column(name = "IMAGE_TITLE")
    private String title;

    @Column(name = "IMAGE_NAME", nullable = false)
    private String name;
    @Column(name = "IMAGE_PATH", nullable = false)
    private String path;

    private boolean large;

    private boolean small;

}
