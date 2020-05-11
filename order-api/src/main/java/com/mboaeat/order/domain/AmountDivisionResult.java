package com.mboaeat.order.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AmountDivisionResult {

    Amount quotient;
    Amount remainder;

    public Amount getQuotientPlusRemainder (){
        return quotient.add(remainder);
    }
}
