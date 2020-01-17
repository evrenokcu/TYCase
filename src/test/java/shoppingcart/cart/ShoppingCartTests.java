package shoppingcart.cart;

import org.junit.Assert;
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
    public void addingSameProductShouldIncreaseTheQuantity() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addShoppingCartItem(Product.of("pen", Money.of(100), Category.of("stationary")), NumberOfProducts.of(2));
        shoppingCart.addShoppingCartItem(Product.of("pen", Money.of(100), Category.of("stationary")), NumberOfProducts.of(3));

        Assert.assertEquals(
                NumberOfProducts.of(5),
                shoppingCart.getShoppingCartItems().getOrDefault("pen", null).getProductInShoppingCart().getQuantity());


    }
}