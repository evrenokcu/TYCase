package shoppingcart.cart;

import org.junit.Assert;
import org.junit.Test;
import shoppingcart.product.Category;
import shoppingcart.product.Product;
import shoppingcart.shared.Money;
import shoppingcart.shared.NumberOfProducts;

public class ShoppingCartItemTest {

    @Test
    public void newShoppingCartItemShouldHaveZeroDiscounts() {
        //given
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem(
                ProductInShoppingCart.of(
                        Product.of("almond", Money.of(10.2), Category.of("nuts")),
                        NumberOfProducts.of(2)));
        //when
        //do nothing
        //then

        Assert.assertEquals(Money.Zero, shoppingCartItem.getCampaignDiscount());
        Assert.assertEquals(Money.Zero, shoppingCartItem.getCouponDiscount());
    }

    @Test
    public void campaignDiscountShouldReflectToShoppingCartItem() {
        //given
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem(
                ProductInShoppingCart.of(
                        Product.of("almond", Money.of(10.2), Category.of("nuts")),
                        NumberOfProducts.of(2)));
        //when
        shoppingCartItem.applyCampaignDiscount(Money.of(2.1));
        //then
        Assert.assertEquals(Money.of(2.1), shoppingCartItem.getCampaignDiscount());
    }

    @Test
    public void couponDiscountShouldReflectToShoppingCartItem() {
        //given
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem(
                ProductInShoppingCart.of(
                        Product.of("almond", Money.of(10.2), Category.of("nuts")),
                        NumberOfProducts.of(2)));
        //when
        shoppingCartItem.applyCouponDiscount(Money.of(2.1));
        //then
        Assert.assertEquals(Money.of(2.1), shoppingCartItem.getCouponDiscount());

    }

    @Test
    public void applyingCouponDiscountAgainShouldReplaceTheDiscountValue() {
        //given
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem(
                ProductInShoppingCart.of(
                        Product.of("almond", Money.of(10.2), Category.of("nuts")),
                        NumberOfProducts.of(2)));
        //when
        shoppingCartItem.applyCouponDiscount(Money.of(34));
        shoppingCartItem.applyCouponDiscount(Money.of(2.1));
        //then
        Assert.assertEquals(Money.of(2.1), shoppingCartItem.getCouponDiscount());

    }

    @Test
    public void shoppingCartItemAddQuantityShouldAddToSum() {
        //given
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem(
                ProductInShoppingCart.of(
                        Product.of("almond", Money.of(10.2), Category.of("nuts")),
                        NumberOfProducts.of(2)));
        //when
        shoppingCartItem.addQuantity(NumberOfProducts.of(3));
        //then
        Assert.assertEquals(NumberOfProducts.of(5), shoppingCartItem.getProductInShoppingCart().getQuantity());
    }


}
