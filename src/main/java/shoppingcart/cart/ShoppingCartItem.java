package shoppingcart.cart;

import org.jetbrains.annotations.NotNull;
import shoppingcart.shared.Money;
import shoppingcart.shared.ddd.Entity;

public class ShoppingCartItem extends Entity<ShoppingCartItem> {
    ProductInShoppingCart productInShoppingCart;
    Money couponDiscount = Money.Zero;
    Money campaignDiscount = Money.Zero;

    public ProductInShoppingCart getProductInShoppingCart() {
        return productInShoppingCart;
    }

    public Money getCouponDiscount() {
        return couponDiscount;
    }

    public Money getCampaignDiscount() {
        return campaignDiscount;
    }

    private ShoppingCartItem(@NotNull ProductInShoppingCart productInShoppingCart) {
        this.productInShoppingCart = productInShoppingCart;
    }

    public void applyCouponDiscount(Money discountAmount) {
        this.couponDiscount = this.couponDiscount.add(discountAmount);
    }
}
