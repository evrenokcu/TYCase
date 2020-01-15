package shoppingcart.cart;

import org.jetbrains.annotations.NotNull;
import shoppingcart.discount.Campaign;
import shoppingcart.discount.Coupon;
import shoppingcart.discount.calculators.CampaignDiscountCalculator;
import shoppingcart.discount.calculators.CouponDiscountCalculator;
import shoppingcart.processors.PrintProcessor;
import shoppingcart.processors.ShoppingCartProcessor;
import shoppingcart.product.Product;
import shoppingcart.shared.Money;
import shoppingcart.shared.NumberOfProducts;
import shoppingcart.shared.ddd.AggregateRoot;

import java.math.BigDecimal;
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

    public NumberOfProducts getProductNumbers() {
        long count = shoppingCartItems.values().stream()
                .map(shoppingCartItem -> shoppingCartItem.getProductInShoppingCart().getProduct().getTitle())
                .distinct()
                .count();
        return NumberOfProducts.of((int) count);

    }

    public void applyCampaign(Campaign campaign) {

        if (!this.getTotalAmount().isGreaterThan(Money.Zero)) {
            throw new UnsupportedOperationException("Can not apply discount to an empty basket");
        }

        CampaignDiscountCalculator campaignDiscountCalculator = new CampaignDiscountCalculator(campaign);
        Money discount = campaignDiscountCalculator.calculateDiscount(this);
        this.campaignDiscount = this.campaignDiscount.add(discount);

    }

    public void applyCoupon(Coupon coupon) {
        if (!this.getTotalAmount().isGreaterThan(Money.Zero)) {
            throw new UnsupportedOperationException("Can not apply discount to an empty basket");
        }

        CouponDiscountCalculator couponDiscountCalculator = new CouponDiscountCalculator(coupon);
        Money discount = couponDiscountCalculator.calculateDiscount(this);
        this.couponDiscount = discount;
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
}
