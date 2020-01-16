package shoppingcart.discount.calculators;

import shoppingcart.cart.ShoppingCart;
import shoppingcart.cart.ShoppingCartItem;
import shoppingcart.discount.Coupon;
import shoppingcart.shared.Money;

import java.math.MathContext;

public class CouponDiscountCalculator extends DiscountCalculator {
    private Coupon coupon;

    public CouponDiscountCalculator(Coupon coupon) {
        this.coupon = coupon;
    }

    @Override
    protected void doCalculateDiscount(ShoppingCart shoppingCart) {
        discountResult.setTotalDiscount(calculateDiscount(shoppingCart.getAmountAfterCampaignDiscount(), coupon.getDiscount()));
    }

    @Override
    protected void doCalculateDiscount(ShoppingCart shoppingCart, ShoppingCartItem shoppingCartItem) {

        if (checkItemCondition(shoppingCartItem)) {
            Money lineAmountWithCampaignDiscount = shoppingCartItem.getTotalPrice().deduct(shoppingCartItem.getCampaignDiscount());
            Money lineAmountCouponDiscount = Money.of(lineAmountWithCampaignDiscount.getAmount().multiply(discountResult.getTotalDiscount().getAmount()).divide(shoppingCart.getAmountAfterCampaignDiscount().getAmount(), MathContext.DECIMAL32));
            discountResult.applyDiscountItem(shoppingCartItem.getProductInShoppingCart().getProduct().getTitle(), lineAmountCouponDiscount);
        }
    }

    @Override
    protected boolean doCheckCondition(ShoppingCart shoppingCart) {
        if (!shoppingCart.getTotalAmount().isGreaterThan(Money.Zero)) {
            throw new UnsupportedOperationException("Can not apply discount to an empty basket");
        }
        return shoppingCart.getAmountAfterCampaignDiscount().isGreaterThanOrEqual(coupon.getMinAmount());
    }
}
