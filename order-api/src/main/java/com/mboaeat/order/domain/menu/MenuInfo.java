package com.mboaeat.order.domain.menu;

import com.mboaeat.common.dto.DataStatus;
import com.mboaeat.order.domain.BaseEntity;
import com.mboaeat.order.domain.Menu;
import lombok.*;

import javax.persistence.*;

import static com.mboaeat.common.dto.DataStatus.ACCEPTED;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MENU_INFO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "MENU_INFO_TYPE")
@AttributeOverride(
        name = "id",
        column = @Column(name = "MENU_INFO_ID")
)
public class MenuInfo extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "MENU_ID", nullable = false)
    private Menu menu;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DataStatus status = ACCEPTED;

    @Version
    @Column(name = "MENU_INFO_VERSION", nullable = false)
    @Getter(AccessLevel.NONE)
    private Integer menuInfoVersion;
}
