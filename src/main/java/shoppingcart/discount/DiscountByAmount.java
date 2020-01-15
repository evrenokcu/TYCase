package shoppingcart.discount;

import shoppingcart.shared.Money;

import java.math.BigDecimal;

public class DiscountByAmount extends Discount {
    public DiscountByAmount(BigDecimal discountValue) {
        super(discountValue, DiscountType.Amount);
        if (discountValue.compareTo(BigDecimal.valueOf(0)) != 1) {
            throw new IllegalArgumentException("invalid discount amount");
        }
    }

    @Override
    public Money calculate(Money amount) {
        if (Money.of(this.discountValue).isGreaterThan(amount)) {
            throw new UnsupportedOperationException("Discount amount can not be greater than the amount to appy the discount");
        }
        return Money.of(this.discountValue);
    }
}
