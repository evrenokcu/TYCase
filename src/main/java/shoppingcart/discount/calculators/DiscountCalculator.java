package shoppingcart.discount.calculators;

import shoppingcart.cart.ShoppingCart;
import shoppingcart.cart.ShoppingCartItem;
import shoppingcart.discount.Discount;
import shoppingcart.shared.Money;

public abstract class DiscountCalculator {
    protected DiscountResult discountResult = new DiscountResult();

    public DiscountResult getDiscountResult() {
        return discountResult;
    }

    public Money calculateDiscount(ShoppingCart shoppingCart) {
        if (!this.doCheckCondition(shoppingCart)) {
            return Money.Zero;
        }
        doCalculateDiscount(shoppingCart);
        shoppingCart.getShoppingCartItems().values().stream().forEach(shoppingCartItem -> doCalculateDiscount(shoppingCart, shoppingCartItem));

        return this.discountResult.getTotalDiscount();
    }

    protected boolean doCheckCondition(ShoppingCart shoppingCart) {
        return true;
    }

    protected Money calculateDiscount(Money amount, Discount discount) {
        return discount.calculate(amount);
    }

    protected boolean checkItemCondition(ShoppingCartItem shoppingCartItem) {
        return true;
    }

    protected abstract void doCalculateDiscount(ShoppingCart shoppingCart);

    protected abstract void doCalculateDiscount(ShoppingCart shoppingCart, ShoppingCartItem shoppingCartItem);


}
