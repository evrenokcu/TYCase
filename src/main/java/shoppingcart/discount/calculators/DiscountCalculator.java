package shoppingcart.discount.calculators;

import shoppingcart.cart.ShoppingCart;
import shoppingcart.discount.Discount;
import shoppingcart.shared.Money;

public abstract class DiscountCalculator {

    public Money calculateDiscount(ShoppingCart shoppingCart) {
        if (!this.doCheckCondition(shoppingCart)) {
            return Money.Zero;
        }
        return this.doCalculateDiscount(shoppingCart);
    }

    protected abstract Money doCalculateDiscount(ShoppingCart shoppingCart);

    protected abstract boolean doCheckCondition(ShoppingCart shoppingCart);

    protected Money applyDiscount(Money amount, Discount discount) {
        return discount.calculate(amount);
    }


}
