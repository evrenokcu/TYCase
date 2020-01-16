package shoppingcart.discount.calculators;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import shoppingcart.cart.ProductInShoppingCart;
import shoppingcart.cart.ShoppingCart;
import shoppingcart.cart.ShoppingCartItem;
import shoppingcart.discount.Coupon;
import shoppingcart.discount.Discount;
import shoppingcart.discount.DiscountType;
import shoppingcart.product.Category;
import shoppingcart.product.Product;
import shoppingcart.shared.Money;
import shoppingcart.shared.NumberOfProducts;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.Mockito.when;

public class CouponDiscountCalculatorTests {

    @Test
    public void totalDiscountCheckForDiscountTypeAmount() {

        //given
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
        CouponDiscountCalculator couponDiscountCalculator = new CouponDiscountCalculator(
                Coupon.of(Money.of(10), Discount.of(BigDecimal.valueOf(20), DiscountType.Amount))
        );
        when(mockShoppingCart.getAmountAfterCampaignDiscount()).thenReturn(Money.of(100));
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(1));
        //when
        Money discount = couponDiscountCalculator.calculateDiscount(mockShoppingCart);
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(1));
        //then
        Assert.assertEquals(Money.of(20), discount);
    }

    @Test
    public void totalDiscountCheckForDiscountTypeRate() {

        //given
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
        CouponDiscountCalculator couponDiscountCalculator = new CouponDiscountCalculator(
                Coupon.of(Money.of(10), Discount.of(BigDecimal.valueOf(15), DiscountType.Rate))
        );
        when(mockShoppingCart.getAmountAfterCampaignDiscount()).thenReturn(Money.of(200));
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(1));
        //when
        Money discount = couponDiscountCalculator.calculateDiscount(mockShoppingCart);
        //then
        Assert.assertEquals(Money.of(30), discount);
    }

    @Test
    public void discountShouldNotBeAppliedIfConditionIsNotSatisfied() {

        //given
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
        CouponDiscountCalculator couponDiscountCalculator = new CouponDiscountCalculator(
                Coupon.of(Money.of(201), Discount.of(BigDecimal.valueOf(15), DiscountType.Rate))
        );
        when(mockShoppingCart.getAmountAfterCampaignDiscount()).thenReturn(Money.of(200));
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(1));
        //when
        Money discount = couponDiscountCalculator.calculateDiscount(mockShoppingCart);
        //then
        Assert.assertEquals(Money.Zero, discount);
    }

    @Test
    public void itemDiscountsShouldBeDistributedCorrectForDiscountTypeRate() {

        //given
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
        CouponDiscountCalculator couponDiscountCalculator = new CouponDiscountCalculator(
                Coupon.of(Money.of(100), Discount.of(BigDecimal.valueOf(10), DiscountType.Rate))
        );
        when(mockShoppingCart.getAmountAfterCampaignDiscount()).thenReturn(Money.of(630));
        when(mockShoppingCart.getShoppingCartItems()).thenReturn(createShoppingCartItems());
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(1));

        //when
        Money discount = couponDiscountCalculator.calculateDiscount(mockShoppingCart);
        //then
        Assert.assertEquals(Money.of(20), couponDiscountCalculator.getDiscountResult().getItemDiscounts().getOrDefault("almond", null));
        Assert.assertEquals(Money.of(20), couponDiscountCalculator.getDiscountResult().getItemDiscounts().getOrDefault("hazelnut", null));
        Assert.assertEquals(Money.of(30), couponDiscountCalculator.getDiscountResult().getItemDiscounts().getOrDefault("iphone", null));
    }

    @Test
    public void itemDiscountsShouldBeDistributedCorrectForDiscountTypeAmount() {

        //given
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
        CouponDiscountCalculator couponDiscountCalculator = new CouponDiscountCalculator(
                Coupon.of(Money.of(100), Discount.of(BigDecimal.valueOf(315), DiscountType.Amount))
        );
        when(mockShoppingCart.getAmountAfterCampaignDiscount()).thenReturn(Money.of(630));
        when(mockShoppingCart.getShoppingCartItems()).thenReturn(createShoppingCartItems());

        Map<String, ShoppingCartItem> cartItems = mockShoppingCart.getShoppingCartItems();
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(1));
        //when
        Money discount = couponDiscountCalculator.calculateDiscount(mockShoppingCart);
        //then
        Assert.assertEquals(Money.of(100), couponDiscountCalculator.getDiscountResult().getItemDiscounts().getOrDefault("almond", null));
        Assert.assertEquals(Money.of(100), couponDiscountCalculator.getDiscountResult().getItemDiscounts().getOrDefault("hazelnut", null));
        Assert.assertEquals(Money.of(150), couponDiscountCalculator.getDiscountResult().getItemDiscounts().getOrDefault("iphone", null));
    }

    @Test
    public void shouldNotApplyDiscountIfMinimumAmountIsNotSatisfied() {
        //given
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
        CouponDiscountCalculator couponDiscountCalculator = new CouponDiscountCalculator(Coupon.of(Money.of(100), Discount.of(BigDecimal.valueOf(10), DiscountType.Amount)));
        when(mockShoppingCart.getAmountAfterCampaignDiscount()).thenReturn(Money.of(99));
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(1));
        //when
        Money discount = couponDiscountCalculator.calculateDiscount(mockShoppingCart);
        //then
        Assert.assertEquals(Money.Zero, discount);

    }

    @Test
    public void shouldApplyDiscountIfMinimumAmountIsEqualToTheTotalAmount() {
        //given
        Money totalAmount = Money.of(70);

        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);

        CouponDiscountCalculator couponDiscountCalculator = new CouponDiscountCalculator(Coupon.of(totalAmount, Discount.of(BigDecimal.valueOf(10), DiscountType.Amount)));
        when(mockShoppingCart.getAmountAfterCampaignDiscount()).thenReturn(Money.of(70));
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(1));
        //when
        Money discount = couponDiscountCalculator.calculateDiscount(mockShoppingCart);
        //then
        Assert.assertEquals(Money.of(10), (Money) discount);


    }

    @Test
    public void shouldApplyDiscountIfMinimumAmountIsLessThanTheTotalAmount() {
        //given
        Money totalAmount = Money.of(69);
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);

        CouponDiscountCalculator couponDiscountCalculator = new CouponDiscountCalculator(Coupon.of(totalAmount, Discount.of(BigDecimal.valueOf(10), DiscountType.Amount)));
        when(mockShoppingCart.getAmountAfterCampaignDiscount()).thenReturn(Money.of(70));
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(1));
        //when
        Money discount = couponDiscountCalculator.calculateDiscount(mockShoppingCart);
        //then
        Assert.assertEquals(Money.of(10), (Money) discount);

    }

    @Test
    public void shouldApplyCouponDiscountOnTopOfCampaignDiscount() {
        //given
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
        CouponDiscountCalculator couponDiscountCalculator = new CouponDiscountCalculator(
                Coupon.of(Money.of(100), Discount.of(BigDecimal.valueOf(25), DiscountType.Rate))
        );
        when(mockShoppingCart.getAmountAfterCampaignDiscount()).thenReturn(Money.of(350));
        when(mockShoppingCart.getShoppingCartItems()).thenReturn(createShoppingCartItemsWithAppliedCampaignDiscounts());
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(1));

        //when
        Money discount = couponDiscountCalculator.calculateDiscount(mockShoppingCart);
        //then
        Assert.assertEquals(Money.of(25), couponDiscountCalculator.getDiscountResult().getItemDiscounts().getOrDefault("almond", null));
        Assert.assertEquals(Money.of(25), couponDiscountCalculator.getDiscountResult().getItemDiscounts().getOrDefault("hazelnut", null));
        Assert.assertEquals(Money.of(37.5), couponDiscountCalculator.getDiscountResult().getItemDiscounts().getOrDefault("iphone", null));

    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowExceptionIfBasketIsEmpty() {
        //given
        ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
        CouponDiscountCalculator couponDiscountCalculator = new CouponDiscountCalculator(
                Coupon.of(Money.of(100), Discount.of(BigDecimal.valueOf(25), DiscountType.Rate))
        );
        when(mockShoppingCart.getTotalAmount()).thenReturn(Money.of(0));

        //when
        Money discount = couponDiscountCalculator.calculateDiscount(mockShoppingCart);
        //then
        //throws exception
    }

    private Map<String, ShoppingCartItem> createShoppingCartItems() {
        Map<String, ShoppingCartItem> shoppingCartItems = Map.of(
                "almond", new ShoppingCartItem(ProductInShoppingCart.of(Product.of("almond", Money.of(50), Category.of("nuts")), NumberOfProducts.of(4))),
                "hazelnut", new ShoppingCartItem(ProductInShoppingCart.of(Product.of("hazelnut", Money.of(40), Category.of("nuts")), NumberOfProducts.of(5))),
                "iphone", new ShoppingCartItem(ProductInShoppingCart.of(Product.of("iphone", Money.of(150), Category.of("technology")), NumberOfProducts.of(2))));


        return shoppingCartItems;
    }

    private Map<String, ShoppingCartItem> createShoppingCartItemsWithAppliedCampaignDiscounts() {
        ShoppingCartItem almond = new ShoppingCartItem(ProductInShoppingCart.of(Product.of("almond", Money.of(50), Category.of("nuts")), NumberOfProducts.of(4)));
        almond.applyCampaignDiscount(Money.of(100));

        ShoppingCartItem hazelnut = new ShoppingCartItem(ProductInShoppingCart.of(Product.of("hazelnut", Money.of(40), Category.of("nuts")), NumberOfProducts.of(5)));
        hazelnut.applyCampaignDiscount(Money.of(100));

        ShoppingCartItem iphone = new ShoppingCartItem(ProductInShoppingCart.of(Product.of("iphone", Money.of(150), Category.of("technology")), NumberOfProducts.of(2)));
        iphone.applyCampaignDiscount(Money.of(150));

        Map<String, ShoppingCartItem> shoppingCartItems = Map.of(
                "almond", almond,
                "hazelnut", hazelnut,
                "iphone", iphone);


        return shoppingCartItems;
    }
}
