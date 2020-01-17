package shoppingcart.discount.calculators;

import shoppingcart.cart.ShoppingCart;
import shoppingcart.cart.ShoppingCartItem;
import shoppingcart.discount.Campaign;
import shoppingcart.discount.DiscountType;
import shoppingcart.shared.Money;

public class CampaignDiscountCalculator extends DiscountCalculator {
    private Campaign campaign;

    public CampaignDiscountCalculator(Campaign campaign) {
        this.campaign = campaign;
    }

    @Override
    protected void doCalculateDiscount(ShoppingCart shoppingCart, ShoppingCartItem shoppingCartItem) {
        if (checkItemCondition(shoppingCartItem)) {
            Money itemDiscount = calculateDiscount(shoppingCartItem.getTotalPrice(), campaign.getDiscount());

            if (DiscountType.Amount == campaign.getDiscount().getDiscountType()) {
                itemDiscount = itemDiscount.multiply(shoppingCartItem.getProductInShoppingCart().getQuantity());
            }
            Money discountToApply = itemDiscount;
            discountResult.addToTotalDiscount(discountToApply);
            discountResult.applyDiscountItem(shoppingCartItem.getProductInShoppingCart().getProduct().getTitle(), discountToApply);
        }
    }

    @Override
    protected boolean checkItemCondition(ShoppingCartItem shoppingCartItem) {
        return shoppingCartItem.getProductInShoppingCart().getProduct().getCategory().isSubCategoryOrEquals(campaign.getCategory());
    }

    @Override
    protected void doCalculateDiscount(ShoppingCart shoppingCart) {

    }

    @Override
    protected boolean doCheckCondition(ShoppingCart shoppingCart) {
        if (!shoppingCart.getTotalAmount().isGreaterThan(Money.Zero)) {
            throw new UnsupportedOperationException("Can not apply discount to an empty basket");
        }
        return shoppingCart.getProductNumbers(campaign.getCategory()).isGreaterThanOrEqual(campaign.getNumberOfProducts());
    }
}
