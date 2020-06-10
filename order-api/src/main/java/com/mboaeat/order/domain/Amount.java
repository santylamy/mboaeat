package com.mboaeat.order.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

@Data
@NoArgsConstructor
@Embeddable
public class Amount implements Comparable<Amount>, Serializable {

    private static final int HOUDRED = 100;

    @Column(name = "AMOUNT", scale = 4)
    private BigDecimal value;

    Amount(double value){
        this(BigDecimal.valueOf(value));
    }

    @Builder
    public Amount(BigDecimal value) {
        this.value = scale4(value);
    }

    private BigDecimal scale(BigDecimal value, int scale){
        return value.setScale(scale, RoundingMode.HALF_UP);
    }

    private BigDecimal scale4(BigDecimal value){
        return value.setScale(4, RoundingMode.HALF_UP);
    }

    @Override
    public int compareTo(Amount o) {
        return value.compareTo(o.value);
    }

    public static Amount zero(){
        return new Amount(0);
    }

    @Transient
    public static Amount one(){
        return new Amount(1);
    }

    @Transient
    public boolean isZero(){
        return zero().equals(this);
    }

    @Transient
    public boolean isNegative(){
        return value.compareTo(zero().value) < 0;
    }

    @Transient
    public boolean isPositive(){
        return !isNegative();
    }

    public static Amount sum(Amount... amounts) {
        Amount result = zero();

        for (Amount amount: amounts){
            result = result.add(amount);
        }
        return result;
    }

    public static Amount max(Amount... amounts){
        return Arrays.stream(amounts).max((o1, o2) -> o1.compareTo(o2)).orElseGet(Amount::zero);
    }

    public static Amount min(Amount... amounts){
        return Arrays.stream(amounts).min((o1, o2) -> o1.compareTo(o2)).orElseGet(Amount::zero);
    }

    public Amount multiply(double multiplier) {
        return new Amount(value.multiply(scale4(BigDecimal.valueOf(multiplier))));
    }

    public Amount calculatePercentage(Integer percentage) {
        if (percentage.doubleValue() == 0) {
            return zero();
        }
        return multiply(percentage).dividedBy(HOUDRED).getQuotient();
    }

    public Amount calculatePercentage(BigDecimal percentage) {
        if (percentage.doubleValue() == 0) {
            return zero();
        }
        return multiply(percentage.doubleValue()).dividedBy(HOUDRED).getQuotient();
    }

    public AmountDivisionResult dividedBy(int divisor){
        return this.dividedBy(divisor, 4);
    }

    private AmountDivisionResult dividedBy(int divisor, int scale) {
        BigDecimal quotient =
                scale(value, scale)
                .divide(
                        scale(new BigDecimal(divisor), scale),
                        scale,
                        RoundingMode.HALF_UP
                );

        BigDecimal remainder =
                value.subtract(
                        quotient.multiply(
                                scale(new BigDecimal(divisor), scale)
                        )
                );
        return AmountDivisionResult.builder().quotient(new Amount(quotient)).remainder(new Amount(remainder)).build();
    }

    public Amount add(Amount other) {
        return other != null ? new Amount(value.add(other.getValue())) : new Amount(this.getValue());
    }

    public Amount subtract (Amount other) {
        return other != null ? new Amount(value.subtract(other.getValue())) : new Amount(this.getValue());
    }
}
