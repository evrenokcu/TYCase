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
                .mapToDouble(shoppingCartItem -> shoppingCartItem.getTotalPrice().getAmount().doubleValue())
                .sum();

        return applyDiscount(Money.of(sum), campaign.getDiscount());


    }

    private Money applyDiscount(Money amount, Discount discount) {
        return discount.calculate(amount);
    }

    @Override
    protected boolean checkCondition(ShoppingCart shoppingCart) {
        return false;
    }
}
