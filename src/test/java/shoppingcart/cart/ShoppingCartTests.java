package shoppingcart.cart;

import org.junit.Test;
import shoppingcart.discount.Campaign;
import shoppingcart.discount.Coupon;
import shoppingcart.discount.Discount;
import shoppingcart.product.Category;
import shoppingcart.product.Product;
import shoppingcart.shared.Money;
import shoppingcart.shared.NumberOfProducts;

public class ShoppingCartTests {

    @Test
    public void print() {
        ShoppingCart cart = new ShoppingCart();
        cart.addShoppingCartItem(Product.of("almond", Money.of(1.4), Category.of("nuts")), NumberOfProducts.of(3));
        cart.applyCampaign(Campaign.of(Category.of("nuts"), NumberOfProducts.of(1), Discount.ofRate(25)));
        cart.applyCoupon(Coupon.of(Money.Zero, Discount.ofAmount(1)));
        cart.print();

    }
}
