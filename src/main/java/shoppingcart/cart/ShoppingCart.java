package shoppingcart.cart;

import org.jetbrains.annotations.NotNull;
import shoppingcart.discount.Campaign;
import shoppingcart.discount.Coupon;
import shoppingcart.discount.calculators.DiscountResult;
import shoppingcart.processors.CampaignProcessor;
import shoppingcart.processors.CouponProcessor;
import shoppingcart.processors.DeliveryCostProcessor;
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
    private Money deliveryCost = Money.Zero;

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

    public Money getCampaignDiscount() {
        return this.campaignDiscount;
    }

    public Money getAmountAfterCampaignDiscount() {
        return this.getTotalAmount().deduct(this.getCampaignDiscount());
    }

    public void setDeliveryCost(Money deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Money getCouponDiscount() {
        return this.couponDiscount;
    }

    public Money getTotalAmountAfterDiscount() {
        return getTotalAmount().deduct(getCampaignDiscount()).deduct(getCouponDiscount());
    }

    public Money getDeliveryCost() {

        if (Money.Zero.equals(this.deliveryCost)) {
            DeliveryCostProcessor deliveryCostProcessor = new DeliveryCostProcessor();
            deliveryCostProcessor.process(this);
        }
        return deliveryCost;
    }

    public NumberOfProducts getNumberOfDeliveries() {
        long count = this.getShoppingCartItems().values().stream()
                .map(shoppingCartItem -> shoppingCartItem.getProductInShoppingCart().getProduct().getCategory())
                .distinct()
                .count();
        return NumberOfProducts.of((int) count);
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

    public void print() {
        PrintProcessor processor = new PrintProcessor();
        processor.process(this);
    }


}
