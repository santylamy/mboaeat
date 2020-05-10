package com.mboaeat.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("STRUCTURED")
public class StructuredMenu extends Menu {

    @ElementCollection
    @CollectionTable(name = "IMAGES", joinColumns = {@JoinColumn(name = "SOURCE_ID")})
    @SequenceGenerator(name = Image.ID_GENERATOR, sequenceName = "SEQ_IMAGES", allocationSize = 1)
    @org.hibernate.annotations.CollectionId(
            columns = {@Column(name = "IMAGE_ID")},
            type = @org.hibernate.annotations.Type(type = "long"),
            generator = Image.ID_GENERATOR)
    private List<Image> images;
}
