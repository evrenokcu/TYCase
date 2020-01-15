package shoppingcart.discount;

import org.jetbrains.annotations.NotNull;
import shoppingcart.shared.Money;

import java.math.BigDecimal;

public class DiscountByRate extends Discount {
    protected DiscountByRate(@NotNull BigDecimal discountValue) {
        super(discountValue, DiscountType.Rate);

        if (discountValue.compareTo(BigDecimal.valueOf(0)) != 1 || discountValue.compareTo(BigDecimal.valueOf(100)) == 100) {
            throw new IllegalArgumentException("Discount Rate out of bounds");
        }

    }

    @Override
    public Money calculate(Money amount) {
        return amount.percentageOf(discountValue);
    }
}
