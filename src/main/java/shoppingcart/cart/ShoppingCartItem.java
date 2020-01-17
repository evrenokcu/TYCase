package shoppingcart.cart;

import org.jetbrains.annotations.NotNull;
import shoppingcart.shared.Money;
import shoppingcart.shared.NumberOfProducts;
import shoppingcart.shared.ddd.Entity;

public class ShoppingCartItem extends Entity<ShoppingCartItem> {
    private ProductInShoppingCart productInShoppingCart;
    private Money couponDiscount = Money.Zero;
    private Money campaignDiscount = Money.Zero;

    public ShoppingCartItem(@NotNull ProductInShoppingCart productInShoppingCart) {
        this.productInShoppingCart = productInShoppingCart;
    }

    public ProductInShoppingCart getProductInShoppingCart() {
        return productInShoppingCart;
    }

    public Money getCouponDiscount() {
        return couponDiscount;
    }

    public Money getCampaignDiscount() {
        return campaignDiscount;
    }

    public Money getTotalPrice() {
        return productInShoppingCart.getTotalPrice();
    }

    public Money getTotalDiscount() {
        return getCouponDiscount().add(getCampaignDiscount());
    }


    public void applyCouponDiscount(@NotNull Money discountAmount) {
        this.couponDiscount = discountAmount;
    }

    public void applyCampaignDiscount(@NotNull Money discountAmount) {
        this.campaignDiscount = this.campaignDiscount.add(discountAmount);
    }

    public ShoppingCartItem addQuantity(@NotNull NumberOfProducts quantity) {
        productInShoppingCart = productInShoppingCart.addQuantity(quantity);
        return this;
    }
}
