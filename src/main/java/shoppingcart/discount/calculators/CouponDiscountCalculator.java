package shoppingcart.discount.calculators;

import shoppingcart.cart.ShoppingCart;
import shoppingcart.discount.Coupon;
import shoppingcart.discount.Discount;
import shoppingcart.shared.Money;

public class CouponDiscountCalculator extends DiscountCalculator {

    private Coupon coupon;

    public CouponDiscountCalculator(Coupon coupon) {
        this.coupon = coupon;
    }

    @Override
    protected Money doCalculateDiscount(ShoppingCart shoppingCart) {
        return null;
    }

    @Override
    protected boolean checkCondition(ShoppingCart shoppingCart) {
        return false;
    }
}
