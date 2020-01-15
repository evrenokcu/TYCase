package shoppingcart.discount.calculators;

import shoppingcart.cart.ShoppingCart;
import shoppingcart.discount.Campaign;
import shoppingcart.discount.Discount;
import shoppingcart.shared.Money;

public class CampaignDiscountCalculator extends DiscountCalculator {

    private Campaign campaign;

    public CampaignDiscountCalculator(Campaign campaign) {
        this.campaign = campaign;
    }


    @Override
    protected Money doCalculateDiscount(ShoppingCart shoppingCart) {

        double sum = shoppingCart.getShoppingCartItems().values().stream()
                .filter(shoppingCartItem ->
                        shoppingCartItem.getProductInShoppingCart().getProduct()
                                .getCategory().isSubCategoryOrEquals(campaign.getCategory()))
                .mapToDouble(shoppingCartItem ->
                        shoppingCartItem.applyCampaignDiscount(
                                applyDiscount(shoppingCartItem.getTotalPrice(), campaign.getDiscount())).getAmount().doubleValue())
                .sum();
        return Money.of(sum);

    }


    @Override
    protected boolean doCheckCondition(ShoppingCart shoppingCart) {
        return shoppingCart.getProductNumbers().isGreaterThanOrEqual(campaign.getNumberOfProducts());
    }
}
