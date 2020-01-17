package shoppingcart.processors;

import shoppingcart.discount.calculators.DeliveryCostCalculator;
import shoppingcart.cart.ShoppingCart;
import shoppingcart.shared.Money;

public class DeliveryCostProcessor extends ShoppingCartProcessor {
    private final Money costPerDelivery = Money.of(1.2);
    private final Money costPerProduct = Money.of(1.3);
    private final Money fixedDeliveryCost = Money.of(2.99);

    @Override
    protected void afterProcess(ShoppingCart shoppingCart) {
        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(costPerDelivery, costPerProduct, fixedDeliveryCost);
        Money deliveryCost = deliveryCostCalculator.calculate(shoppingCart);
        shoppingCart.setDeliveryCost(deliveryCost);
    }
}
