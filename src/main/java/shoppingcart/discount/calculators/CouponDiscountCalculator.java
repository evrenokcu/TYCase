package shoppingcart.discount.calculators;

import shoppingcart.cart.ShoppingCart;
import shoppingcart.discount.Coupon;
import shoppingcart.shared.Money;

public class CouponDiscountCalculator extends DiscountCalculator {

    private Coupon coupon;

    public CouponDiscountCalculator(Coupon coupon) {
        this.coupon = coupon;
    }

    @Override
    protected Money doCalculateDiscount(ShoppingCart shoppingCart) {

        Money sum = shoppingCart.getTotalAmount();
        Money campaignDiscount = shoppingCart.getCampaignDiscount();
        Money amountWithCampaignDiscount = sum.deduct(campaignDiscount);

        Money totalDiscount = applyDiscount(amountWithCampaignDiscount, coupon.getDiscount());

        shoppingCart.getShoppingCartItems().values().stream()
                .forEach(shoppingCartItem -> {
                    Money lineAmountWithCampaignDiscount = shoppingCartItem.getTotalPrice().deduct(shoppingCartItem.getCampaignDiscount());
                    Money lineAmountCouponDiscount = Money.of(lineAmountWithCampaignDiscount.getAmount().multiply(totalDiscount.getAmount()).divide(amountWithCampaignDiscount.getAmount()));


                    shoppingCartItem.applyCouponDiscount(lineAmountCouponDiscount);

                });

        return totalDiscount;
    }

    @Override
    protected boolean doCheckCondition(ShoppingCart shoppingCart) {

        return shoppingCart.getTotalAmount().isGreaterThanOrEqual(coupon.getMinAmount());

    }


}
