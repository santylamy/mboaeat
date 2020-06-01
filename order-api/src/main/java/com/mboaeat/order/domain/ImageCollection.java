package com.mboaeat.order.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static com.mboaeat.domain.CollectionsUtils.newArrayList;

@Data
@Embeddable
public class ImageCollection implements Serializable {

    @ElementCollection
    @CollectionTable(name = "IMAGES", joinColumns = {@JoinColumn(name = "SOURCE_ID")})
    @SequenceGenerator(name = Image.ID_GENERATOR, sequenceName = "SEQ_IMAGES", allocationSize = 1)
    @org.hibernate.annotations.CollectionId(
            columns = {@Column(name = "IMAGE_ID")},
            type = @org.hibernate.annotations.Type(type = "long"),
            generator = Image.ID_GENERATOR)
    private List<Image> images = newArrayList();

    public void addImage(Image image) {
        images.add(image);
    }

}
