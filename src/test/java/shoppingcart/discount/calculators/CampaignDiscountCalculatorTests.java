package shoppingcart.discount.calculators;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import shoppingcart.cart.ProductInShoppingCart;
import shoppingcart.cart.ShoppingCart;
import shoppingcart.cart.ShoppingCartItem;
import shoppingcart.discount.Campaign;
import shoppingcart.discount.Discount;
import shoppingcart.discount.DiscountType;
import shoppingcart.product.Category;
import shoppingcart.product.Product;
import shoppingcart.shared.Money;
import shoppingcart.shared.NumberOfProducts;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CampaignDiscountCalculatorTests {

    @Test
    public void totalDiscountCheckForDiscountTypeAmount() {

        //given
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
        CampaignDiscountCalculator campaignDiscountCalculator = new CampaignDiscountCalculator(
                Campaign.of(Category.of("technology"), NumberOfProducts.of(1), Discount.of(BigDecimal.valueOf(20), DiscountType.Amount)));

        when(mockShoppingCart.getProductNumbers(any())).thenReturn(NumberOfProducts.of(2));
        when(mockShoppingCart.getShoppingCartItems()).thenReturn(createShoppingCartItems());
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(1));
        //when
        Money discount = campaignDiscountCalculator.calculateDiscount(mockShoppingCart);
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(1));
        //then
        Assert.assertEquals(Money.of(40), discount);
    }

    @Test
    public void totalDiscountCheckForDiscountTypeRate() {
        //given
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
        CampaignDiscountCalculator campaignDiscountCalculator = new CampaignDiscountCalculator(
                Campaign.of(Category.of("technology"), NumberOfProducts.of(1), Discount.of(BigDecimal.valueOf(10), DiscountType.Rate)));

        when(mockShoppingCart.getProductNumbers(any())).thenReturn(NumberOfProducts.of(2));
        when(mockShoppingCart.getShoppingCartItems()).thenReturn(createShoppingCartItems());
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(1));
        //when
        Money discount = campaignDiscountCalculator.calculateDiscount(mockShoppingCart);
        //then
        Assert.assertEquals(Money.of(30), discount);

    }

    @Test
    public void discountShouldNotBeAppliedIfConditionIsNotSatisfiedForNumberOfProducts() {

        //given
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
        CampaignDiscountCalculator campaignDiscountCalculator = new CampaignDiscountCalculator(
                Campaign.of(Category.of("technology"), NumberOfProducts.of(5), Discount.of(BigDecimal.valueOf(10), DiscountType.Rate)));

        when(mockShoppingCart.getProductNumbers(any())).thenReturn(NumberOfProducts.of(2));
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(1));
        //when
        Money discount = campaignDiscountCalculator.calculateDiscount(mockShoppingCart);
        //then
        Assert.assertEquals(Money.Zero, discount);
    }

    @Test
    public void discountShouldNotBeAppliedIfConditionIsNotSatisfiedForCategory() {

        //given
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
        CampaignDiscountCalculator campaignDiscountCalculator = new CampaignDiscountCalculator(
                Campaign.of(Category.of("Non-Existing"), NumberOfProducts.of(2), Discount.of(BigDecimal.valueOf(10), DiscountType.Rate)));

        when(mockShoppingCart.getProductNumbers(any())).thenReturn(NumberOfProducts.of(5));
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(1));
        //when
        Money discount = campaignDiscountCalculator.calculateDiscount(mockShoppingCart);
        //then
        Assert.assertEquals(Money.Zero, discount);
    }

    @Test
    public void discountShouldBeAppliedIfConditionIsSatisfiedForParentCategory() {

        //given
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
        CampaignDiscountCalculator campaignDiscountCalculator = new CampaignDiscountCalculator(
                Campaign.of(Category.of("food"), NumberOfProducts.of(2), Discount.of(BigDecimal.valueOf(10), DiscountType.Rate)));

        when(mockShoppingCart.getProductNumbers(any())).thenReturn(NumberOfProducts.of(5));
        when(mockShoppingCart.getShoppingCartItems()).thenReturn(createShoppingCartItems());
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(1));

        //when
        Money discount = campaignDiscountCalculator.calculateDiscount(mockShoppingCart);
        //then
        Assert.assertEquals(Money.of(40), discount);
    }

    @Test
    public void itemDiscountsShouldBeDistributedCorrectForDiscountTypeRate() {

        //given
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
        CampaignDiscountCalculator campaignDiscountCalculator = new CampaignDiscountCalculator(
                Campaign.of(Category.of("nuts", Category.of("food")), NumberOfProducts.of(1), Discount.of(BigDecimal.valueOf(10), DiscountType.Rate)));
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(700));
        when(mockShoppingCart.getShoppingCartItems()).thenReturn(createShoppingCartItems());
        when(mockShoppingCart.getProductNumbers(any())).thenReturn(NumberOfProducts.of(2));

//        Map<String, ShoppingCartItem> cartItems = mockShoppingCart.getShoppingCartItems();
        //when
        Money discount = campaignDiscountCalculator.calculateDiscount(mockShoppingCart);
        //then
        Assert.assertEquals(Money.of(20), campaignDiscountCalculator.getDiscountResult().getItemDiscounts().getOrDefault("almond", null));
        Assert.assertEquals(Money.of(20), campaignDiscountCalculator.getDiscountResult().getItemDiscounts().getOrDefault("hazelnut", null));
        Assert.assertEquals(null, campaignDiscountCalculator.getDiscountResult().getItemDiscounts().getOrDefault("iphone", null));
    }

    @Test
    public void itemDiscountsShouldBeDistributedCorrectForDiscountTypeAmount() {

        //given
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
        CampaignDiscountCalculator campaignDiscountCalculator = new CampaignDiscountCalculator(
                Campaign.of(Category.of("nuts", Category.of("food")), NumberOfProducts.of(1), Discount.of(BigDecimal.valueOf(10), DiscountType.Amount)));

        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(700));
        when(mockShoppingCart.getShoppingCartItems()).thenReturn(createShoppingCartItems());
        when(mockShoppingCart.getProductNumbers(any())).thenReturn(NumberOfProducts.of(2));

//        Map<String, ShoppingCartItem> cartItems = mockShoppingCart.getShoppingCartItems();
        //when
        Money discount = campaignDiscountCalculator.calculateDiscount(mockShoppingCart);
        //then
        Assert.assertEquals(Money.of(40), (campaignDiscountCalculator.getDiscountResult().getItemDiscounts().getOrDefault("almond", null)));
        Assert.assertEquals(Money.of(50), (campaignDiscountCalculator.getDiscountResult().getItemDiscounts().getOrDefault("hazelnut", null)));
        Assert.assertEquals(null, campaignDiscountCalculator.getDiscountResult().getItemDiscounts().getOrDefault("iphone", null));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowExceptionIfBasketIsEmpty() {

        //given
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
        CampaignDiscountCalculator campaignDiscountCalculator = new CampaignDiscountCalculator(
                Campaign.of(Category.of("nuts", Category.of("food")), NumberOfProducts.of(1), Discount.of(BigDecimal.valueOf(10), DiscountType.Amount)));

        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(0));
        //when
        Money discount = campaignDiscountCalculator.calculateDiscount(mockShoppingCart);
        //then

    }


    private Map<String, ShoppingCartItem> createShoppingCartItems() {
        Map<String, ShoppingCartItem> shoppingCartItems = Map.of(
                "almond", new ShoppingCartItem(ProductInShoppingCart.of(Product.of("almond", Money.of(50), Category.of("nuts", Category.of("food"))), NumberOfProducts.of(4))),
                "hazelnut", new ShoppingCartItem(ProductInShoppingCart.of(Product.of("hazelnut", Money.of(40), Category.of("nuts", Category.of("food"))), NumberOfProducts.of(5))),
                "iphone", new ShoppingCartItem(ProductInShoppingCart.of(Product.of("iphone", Money.of(150), Category.of("technology")), NumberOfProducts.of(2))));


        return shoppingCartItems;
    }

}
