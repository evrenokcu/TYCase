package shoppingcart.processors;

import shoppingcart.cart.ShoppingCart;
import shoppingcart.cart.ShoppingCartItem;
import shoppingcart.discount.Coupon;
import shoppingcart.discount.calculators.CouponDiscountCalculator;

public class CouponProcessor extends ShoppingCartProcessor {

    Coupon coupon;

    public CouponProcessor(Coupon coupon) {
        this.coupon = coupon;
    }

    @Override
    protected void beforeProcess(ShoppingCart shoppingCart) {
        CouponDiscountCalculator couponDiscountCalculator = new CouponDiscountCalculator(this.coupon);
        couponDiscountCalculator.calculateDiscount(shoppingCart);
        shoppingCart.applyCouponDiscountResult(couponDiscountCalculator.getDiscountResult());
    }
}
