package shoppingcart.cart;

import shoppingcart.shared.Money;

import java.math.BigDecimal;

public class DeliveryCostCalculator {
    private Money costPerDelivery;
    private Money costPerProduct;
    private Money fixedCost;

    public DeliveryCostCalculator(Money costPerDelivery, Money costPerProduct, Money fixedCost) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = fixedCost;
    }

    public Money calculate(ShoppingCart shoppingCart) {
        return Money.of(
                costPerDelivery.getAmount().multiply(BigDecimal.valueOf(shoppingCart.getNumberOfDeliveries().getValue()))
                        .add(costPerProduct.getAmount().multiply(BigDecimal.valueOf(shoppingCart.getProductNumbers().getValue())))
                        .add(fixedCost.getAmount())
        );
    }
}
