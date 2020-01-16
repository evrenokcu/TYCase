package shoppingcart.processors;

import org.junit.Test;
import shoppingcart.cart.ShoppingCart;
import shoppingcart.discount.Campaign;
import shoppingcart.discount.Coupon;
import shoppingcart.discount.Discount;
import shoppingcart.discount.DiscountType;
import shoppingcart.product.Category;
import shoppingcart.product.Product;
import shoppingcart.shared.Money;
import shoppingcart.shared.NumberOfProducts;

import java.math.BigDecimal;

public class CouponProcessorTests {

    @Test
    public void test() {
        CouponProcessor couponProcessor = new CouponProcessor(Coupon.of(Money.of(10),
                Discount.of(BigDecimal.valueOf(7), DiscountType.Amount)));

        CouponProcessor otherCouponProcessor = new CouponProcessor(Coupon.of(Money.of(10),
                Discount.of(BigDecimal.valueOf(10), DiscountType.Rate)));

        CampaignProcessor campaignProcessor = new CampaignProcessor(Campaign.of(Category.of("technology"), NumberOfProducts.of(1), Discount.of(BigDecimal.valueOf(10), DiscountType.Amount)));

        PrintProcessor printProcessor = new PrintProcessor();

        couponProcessor.setNext(otherCouponProcessor);
        otherCouponProcessor.setNext(campaignProcessor);
        campaignProcessor.setNext(printProcessor);

        couponProcessor.process(createShoppingCart());

    }

    private ShoppingCart createShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addShoppingCartItem(Product.of("almond", Money.of(50), Category.of("nuts")), NumberOfProducts.of(4));
        shoppingCart.addShoppingCartItem(Product.of("hazelnut", Money.of(40), Category.of("nuts")), NumberOfProducts.of(5));
        shoppingCart.addShoppingCartItem(Product.of("iphone", Money.of(150), Category.of("technology")), NumberOfProducts.of(2));
        return shoppingCart;
    }
}
