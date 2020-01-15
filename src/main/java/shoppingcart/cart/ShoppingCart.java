package shoppingcart.cart;

import org.jetbrains.annotations.NotNull;
import shoppingcart.product.Product;
import shoppingcart.shared.NumberOfProducts;
import shoppingcart.shared.ddd.AggregateRoot;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart extends AggregateRoot<ShoppingCart> {

    public Map<String, ShoppingCartItem> getShoppingCartItems() {
        return  shoppingCartItems;
    }

    private Map<String, ShoppingCartItem> shoppingCartItems = new HashMap<>();

    public void addShoppingCartItem(@NotNull Product product, @NotNull NumberOfProducts quantity) {
        shoppingCartItems.compute(product.getTitle(), (key, shoppingCartItem) ->
                shoppingCartItem == null
                        ? new ShoppingCartItem(ProductInShoppingCart.of(product, quantity)) :
                        shoppingCartItem.addQuantity(quantity));
    }




}
