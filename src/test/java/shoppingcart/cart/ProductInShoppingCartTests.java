package shoppingcart.cart;

import org.junit.Assert;
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

    @Test
    public void changeOfQuantityShouldReflectToTheSum() {
        //Given //4 product
        Product product = Product.of("almond", Money.of(1.2), Category.of("nuts"));
        ProductInShoppingCart productInShoppingCart = ProductInShoppingCart.of(product, NumberOfProducts.of(4));
        //when //adding 3 product
        ProductInShoppingCart newProductInShoppingCart = productInShoppingCart.addQuantity(NumberOfProducts.of(3));
        //then //should sum to 7
        Assert.assertEquals(NumberOfProducts.of(7), newProductInShoppingCart.getQuantity());

    }
}
