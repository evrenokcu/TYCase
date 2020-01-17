package shoppingcart.cart;

import org.junit.Test;
import shoppingcart.discount.Campaign;
import shoppingcart.discount.Coupon;
import shoppingcart.discount.Discount;
import shoppingcart.discount.DiscountType;
import shoppingcart.processors.*;
import shoppingcart.product.Category;
import shoppingcart.product.Product;
import shoppingcart.shared.Money;
import shoppingcart.shared.NumberOfProducts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartBuilder {

    ShoppingCart shoppingCart = new ShoppingCart();
    List<Campaign> campaigns = new ArrayList<>();
    Coupon coupon;
    ShoppingCartProcessorBuilder shoppingCartProcessorBuilder = new ShoppingCartProcessorBuilder();
    ShoppingCartProcessor firstProcessor = null;


    @Test
    public void buildShoppingCart() {
        ShoppingCartBuilder builder = new ShoppingCartBuilder();
        builder.addProduct(Product.of("almond", Money.of(20), Category.of("nuts", Category.of("food"))), NumberOfProducts.of(5))
                .addProduct(Product.of("hazelnut", Money.of(30), Category.of("nuts", Category.of("food"))), NumberOfProducts.of(10))
                .addProduct(Product.of("toblerone", Money.of(25), Category.of("chocolate", Category.of("food"))), NumberOfProducts.of(8))
                .addProduct(Product.of("iphone", Money.of(200), Category.of("technology")), NumberOfProducts.of(2))
                .addCampaign(Campaign.of(Category.of("nuts", Category.of("food")), NumberOfProducts.of(2), Discount.of(BigDecimal.TEN, DiscountType.Amount)))
                .addCampaign(Campaign.of(Category.of("food"), NumberOfProducts.of(3), Discount.of(BigDecimal.TEN, DiscountType.Rate)))
                .addCoupon(Coupon.of(Money.of(790), Discount.of(BigDecimal.TEN, DiscountType.Rate)))
                .build()
                .run();
    }

    private void run() {
        firstProcessor.process(shoppingCart);
    }

    private ShoppingCartBuilder build() {
        this.campaigns.forEach(campaign -> shoppingCartProcessorBuilder.add(new CampaignProcessor(campaign)));
        if (null != coupon) {
            shoppingCartProcessorBuilder.add(new CouponProcessor(coupon));
        }
        shoppingCartProcessorBuilder.add(new DeliveryCostProcessor());
        shoppingCartProcessorBuilder.add(new PrintProcessor());
        firstProcessor = shoppingCartProcessorBuilder.build();
        return this;
    }

    private ShoppingCartBuilder addCoupon(Coupon coupon) {
        this.coupon = coupon;
        return this;
    }

    public ShoppingCartBuilder addProduct(Product product, NumberOfProducts numberOfProducts) {
        shoppingCart.addShoppingCartItem(product, numberOfProducts);
        return this;

    }

    public ShoppingCartBuilder addCampaign(Campaign campaign) {
        this.campaigns.add(campaign);
        return this;
    }

}
