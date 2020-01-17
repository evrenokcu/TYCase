package shoppingcart.processors;

import shoppingcart.cart.ShoppingCart;
import shoppingcart.discount.Campaign;
import shoppingcart.discount.calculators.CampaignDiscountCalculator;

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
