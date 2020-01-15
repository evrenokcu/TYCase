package shoppingcart.discount;

import org.jetbrains.annotations.NotNull;
import shoppingcart.shared.Money;
import shoppingcart.shared.ddd.ValueObject;

import java.math.BigDecimal;
import java.util.Dictionary;

public abstract class Discount extends ValueObject<Discount> {
    protected BigDecimal discountValue;
    protected DiscountType discountType;

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    protected Discount(BigDecimal discountValue, DiscountType discountType) {
        this.discountValue = discountValue;
        this.discountType = discountType;
    }

    public abstract Money calculate(Money amount);

    @Override
    protected boolean equalsCore(Discount other) {
        if ((other.discountValue.compareTo(discountValue)) != 0) return false;
        return discountType == other.discountType;
    }

    @Override
    protected int hashCodeCore() {
        int result = 31 * discountValue.hashCode();
        result = 31 * result + discountType.hashCode();
        return result;
    }

    public static Discount of(@NotNull BigDecimal discountValue, DiscountType discountType) {
        if (null == discountType) {
            throw new IllegalArgumentException("Invalid Discount Type");
        }

        switch (discountType) {
            case Rate:
                return new DiscountByRate(discountValue);
            case Amount:
                return new DiscountByAmount(discountValue);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static Discount ofRate(BigDecimal discountValue) {
        return of(discountValue, DiscountType.Rate);
    }

    public static Discount ofRate(double discountValue) {
        return ofRate(BigDecimal.valueOf(discountValue));
    }

    public static Discount ofAmount(BigDecimal discountValue) {
        return of(discountValue, DiscountType.Amount);
    }

    public static Discount ofAmount(double discountValue) {
        return ofAmount(BigDecimal.valueOf(discountValue));
    }
}
