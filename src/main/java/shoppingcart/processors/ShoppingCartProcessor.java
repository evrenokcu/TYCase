package shoppingcart.processors;

import shoppingcart.cart.ShoppingCart;
import shoppingcart.cart.ShoppingCartItem;

import java.util.Comparator;

public abstract class ShoppingCartProcessor {
    ShoppingCartProcessor next;


    public final void process(ShoppingCart shoppingCart) {
        beforeProcess(shoppingCart);

        shoppingCart.getShoppingCartItems().values().stream()
                .sorted(getShoppingCartItemComparator())
                .forEach(shoppingCartItem -> process(shoppingCartItem));

        afterProcess(shoppingCart);

        if (null != next) {
            next.process(shoppingCart);
        }
    }

    protected void afterProcess(ShoppingCart shoppingCart) {
    }

    protected void process(ShoppingCartItem shoppingCartItem) {
    }

    protected void beforeProcess(ShoppingCart shoppingCart) {
    }

    abstract Comparator<ShoppingCartItem> getShoppingCartItemComparator();


}
