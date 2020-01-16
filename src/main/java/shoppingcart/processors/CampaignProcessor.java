package shoppingcart.processors;

import shoppingcart.cart.ShoppingCart;
import shoppingcart.cart.ShoppingCartItem;
import shoppingcart.discount.Campaign;
import shoppingcart.discount.calculators.CampaignDiscountCalculator;
import shoppingcart.shared.Money;

public class CampaignProcessor extends ShoppingCartProcessor {

    Campaign campaign;

    public CampaignProcessor(Campaign campaign) {
        this.campaign = campaign;
    }

    @Override
    protected void afterProcess(ShoppingCart shoppingCart) {
        CampaignDiscountCalculator campaignDiscountCalculator = new CampaignDiscountCalculator(campaign);
        campaignDiscountCalculator.calculateDiscount(shoppingCart);

        shoppingCart.applyCampaignDiscount(campaignDiscountCalculator.getDiscountResult());
    }


}
