package shoppingcart.cart;

import org.jetbrains.annotations.NotNull;
import shoppingcart.discount.Campaign;
import shoppingcart.discount.Coupon;
import shoppingcart.discount.calculators.DiscountResult;
import shoppingcart.processors.CampaignProcessor;
import shoppingcart.processors.CouponProcessor;
import shoppingcart.processors.PrintProcessor;
import shoppingcart.product.Category;
import shoppingcart.product.Product;
import shoppingcart.shared.Money;
import shoppingcart.shared.NumberOfProducts;
import shoppingcart.shared.ddd.AggregateRoot;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart extends AggregateRoot<ShoppingCart> {

    private Money sum = Money.Zero;
    private Money campaignDiscount = Money.Zero;
    private Money couponDiscount = Money.Zero;

    private final Money costPerDelivery = Money.of(1.2);
    private final Money costPerProduct = Money.of(1.3);
    private final Money fixedDeliveryCost = Money.of(2.99);


    public Map<String, ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }

    private Map<String, ShoppingCartItem> shoppingCartItems = new HashMap<>();

    public void addShoppingCartItem(@NotNull Product product, @NotNull NumberOfProducts quantity) {
        shoppingCartItems.compute(product.getTitle(), (key, shoppingCartItem) ->
                shoppingCartItem == null
                        ? new ShoppingCartItem(ProductInShoppingCart.of(product, quantity)) :
                        shoppingCartItem.addQuantity(quantity));
    }


    public Money getTotalAmount() {
        if (this.sum.equals(Money.Zero)) {
            this.sum = calculateSum();
        }
        return this.sum;
    }

    private Money calculateSum() {
        return Money.of(shoppingCartItems.values().stream().mapToDouble(
                shoppingCartItem -> shoppingCartItem.getTotalPrice().getAmount().doubleValue())
                .sum());

    }


    public void applyCampaign(Campaign campaign) {
        CampaignProcessor campaignProcessor = new CampaignProcessor(campaign);
        campaignProcessor.process(this);
    }

    public void applyCampaignDiscount(DiscountResult discountResult) {
        this.campaignDiscount = this.campaignDiscount.add(discountResult.getTotalDiscount());
        discountResult.getItemDiscounts().forEach(
                (key, discount) -> this.shoppingCartItems.getOrDefault(key, null).applyCampaignDiscount(discount));
    }

    public void applyCoupon(Coupon coupon) {
        CouponProcessor couponProcessor = new CouponProcessor(coupon);
        couponProcessor.process(this);
    }

    public void applyCouponDiscountResult(DiscountResult discountResult) {
        this.couponDiscount = discountResult.getTotalDiscount();
        discountResult.getItemDiscounts().forEach(
                (key, discount) -> this.shoppingCartItems.getOrDefault(key, null).applyCouponDiscount(discount));
    }

    public Money getCampaignDiscount() {
        return this.campaignDiscount;
    }

    public Money getCouponDiscount() {
        return this.couponDiscount;
    }

    public Money getTotalAmountAfterDiscount() {
        return getTotalAmount().deduct(getCampaignDiscount()).deduct(getCouponDiscount());
    }

    public Money getDeliveryCost() {
        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(this.costPerDelivery, costPerProduct, fixedDeliveryCost);
        Money deliveryCost = deliveryCostCalculator.calculate(this);
        return deliveryCost;
    }

    public NumberOfProducts getNumberOfDeliveries() {
        long count = this.getShoppingCartItems().values().stream()
                .map(shoppingCartItem -> shoppingCartItem.getProductInShoppingCart().getProduct().getCategory())
                .distinct()
                .count();
        return NumberOfProducts.of((int) count);
    }


    public void print() {
        PrintProcessor processor = new PrintProcessor();
        processor.process(this);
    }

    public NumberOfProducts getProductNumbers() {
        long count = shoppingCartItems.values().stream()
                .map(shoppingCartItem -> shoppingCartItem.getProductInShoppingCart().getProduct().getTitle())
                .distinct()
                .count();
        return NumberOfProducts.of((int) count);

    }

    public NumberOfProducts getProductNumbers(Category category) {
        long count = shoppingCartItems.values().stream()
                .filter(shoppingCartItem -> shoppingCartItem.getProductInShoppingCart().getProduct().getCategory().isSubCategoryOrEquals(category))
                .map(shoppingCartItem -> shoppingCartItem.getProductInShoppingCart().getProduct().getTitle())
                .distinct()
                .count();
        return NumberOfProducts.of((int) count);
    }

    public Money getAmountAfterCampaignDiscount() {
        return this.getTotalAmount().deduct(this.getCampaignDiscount());
    }
}
