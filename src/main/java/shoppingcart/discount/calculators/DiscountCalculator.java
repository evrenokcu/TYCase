package shoppingcart.discount.calculators;

import shoppingcart.cart.ShoppingCart;
import shoppingcart.shared.Money;

public abstract class DiscountCalculator {

    public Money calculateDiscount(ShoppingCart shoppingCart) {
        if (!this.checkCondition(shoppingCart)) {
            return Money.Zero;
        }
        return this.doCalculateDiscount(shoppingCart);
    }

    protected abstract Money doCalculateDiscount(ShoppingCart shoppingCart);

    protected abstract boolean checkCondition(ShoppingCart shoppingCart);


}
