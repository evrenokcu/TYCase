package shoppingcart.processors;

import org.junit.Test;
import org.mockito.Mockito;
import shoppingcart.cart.ShoppingCart;
import shoppingcart.discount.Campaign;
import shoppingcart.discount.Discount;
import shoppingcart.discount.DiscountType;
import shoppingcart.discount.calculators.DiscountResult;
import shoppingcart.product.Category;
import shoppingcart.shared.Money;
import shoppingcart.shared.NumberOfProducts;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CampaignProcessorTests {

    @Test
    public void verifyDiscountResultIsAppliedToTheShoppingCart() {

        //given
        Campaign campaign = Campaign.of(Category.of("nuts"), NumberOfProducts.of(3), Discount.of(BigDecimal.TEN, DiscountType.Amount));
        ShoppingCart shoppingCart = Mockito.mock(ShoppingCart.class);
        CampaignProcessor campaignProcessor = new CampaignProcessor(campaign);
        when(shoppingCart.getTotalAmount()).thenReturn((Money.of(10)));
        when(shoppingCart.getProductNumbers(any())).thenReturn(NumberOfProducts.of(19));
        //when
        campaignProcessor.process(shoppingCart);
        //then
        verify(shoppingCart).applyCampaignDiscount(any(DiscountResult.class));
    }

}
