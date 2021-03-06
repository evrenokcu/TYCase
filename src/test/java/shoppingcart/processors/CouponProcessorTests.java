package shoppingcart.processors;

import org.junit.Test;
import org.mockito.Mockito;
import shoppingcart.cart.ShoppingCart;
import shoppingcart.discount.Coupon;
import shoppingcart.discount.Discount;
import shoppingcart.discount.DiscountType;
import shoppingcart.discount.calculators.DiscountResult;
import shoppingcart.shared.Money;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CouponProcessorTests {

    @Test
    public void verifyDiscountResultIsAppliedToTheShoppingCart() {

        //given
        Coupon coupon = Coupon.of(Money.Zero, Discount.of(BigDecimal.TEN, DiscountType.Amount));
        ShoppingCart shoppingCart = Mockito.mock(ShoppingCart.class);
        CouponProcessor couponProcessor = new CouponProcessor(coupon);
        when(shoppingCart.getTotalAmount()).thenReturn((Money.of(10)));
        when(shoppingCart.getAmountAfterCampaignDiscount()).thenReturn(Money.of(28));
        //when
        couponProcessor.process(shoppingCart);
        //then
        verify(shoppingCart).applyCouponDiscountResult(any(DiscountResult.class));
    }
}
