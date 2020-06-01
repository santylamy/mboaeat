package com.mboaeat.order.domain;

import com.mboaeat.domain.Periodical;
import com.mboaeat.domain.PeriodicalElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCT_PRICES")
public class ProductPrice implements Serializable, PeriodicalElement<PeriodByDay> {

    @Id
    @Column(name = "PRODUCT_PRICE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorProductPrice")
    @SequenceGenerator(name = "idGeneratorProductPrice", sequenceName = "SEQ_PRODUCT_PRICES", allocationSize = 1)
    private Long id;

    @Embedded
    @AttributeOverrides(
         value = {
                 @AttributeOverride(name = "startDate", column = @Column(name = "DATE_FROM"))
         }
    )
    @Builder.Default
    private PeriodByDay period = PeriodByDay.periodByDayStartingToday();

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "PRODUCT_PRICE")
    )
    private Amount amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Override
    public PeriodicalElement<PeriodByDay> copy(PeriodByDay period) {
        return ProductPrice
                .builder()
                .id(id)
                .period(period)
                .amount(amount)
                .product(product).build();
    }

    @Override
    public PeriodicalElement<PeriodByDay> merge(PeriodicalElement<PeriodByDay> periodical) {
        return this.copy(period.merge(periodical.getPeriod()));
    }

    @Override
    public boolean isContentEqual(PeriodicalElement<PeriodByDay> periodical) {
        if (!(periodical instanceof ProductPrice)){
            return false;
        }

        ProductPrice other = (ProductPrice) periodical;

        return new EqualsBuilder()
                .append(amount, other.amount)
                .append(product, other.product)
                .isEquals();
    }

    @Override
    public int compareTo(Periodical<PeriodByDay> periodical) {
        return period.compareTo(periodical.getPeriod());
    }
}
