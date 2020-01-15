package shoppingcart.cart;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import shoppingcart.product.Category;
import shoppingcart.product.Product;
import shoppingcart.shared.Money;
import shoppingcart.shared.NumberOfProducts;

public class ProductInShoppingCartTests {
    @Test(expected = IllegalArgumentException.class)
    public void zeroProductQuantityShouldThrowException() {
        Product product = Product.of("almond", Money.of(1.2), Category.of("nuts"));
        ProductInShoppingCart.of(product, NumberOfProducts.of(0));
    }
}
