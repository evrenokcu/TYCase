package shoppingcart.discount;

import org.jetbrains.annotations.NotNull;
import shoppingcart.shared.Money;
import shoppingcart.shared.ddd.ValueObject;

public class Coupon extends ValueObject<Coupon> {

    private Money minAmount;
    private Discount discount;

    private Coupon(Money minAmount, Discount discount) {
        this.minAmount = minAmount;
        this.discount = discount;
    }

    public Money getMinAmount() {
        return minAmount;
    }

    public Discount getDiscount() {
        return discount;
    }

    public static Coupon of(@NotNull Money minAmount, @NotNull Discount discount) {
        return new Coupon(minAmount, discount);
    }


    @Override
    protected boolean equalsCore(Coupon other) {
        if (!minAmount.equals(other.minAmount)) return false;
        return discount.equals(other.discount);
    }

    @Override
    protected int hashCodeCore() {
        int result = minAmount.hashCode();
        result = 31 * result + discount.hashCode();
        return result;
    }
}
