package shoppingcart.discount.calculators;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import shoppingcart.cart.ShoppingCart;
import shoppingcart.shared.Money;
import shoppingcart.shared.NumberOfProducts;

import static org.mockito.Mockito.when;

public class DeliveryCostCalculatorTests {
    @Test
    public void checkDeliveryCostFormula() {
        //given
        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(Money.of(10), Money.of(20), Money.of(30));
        ShoppingCart shoppingCart = Mockito.mock(ShoppingCart.class);
        when(shoppingCart.getNumberOfDeliveries()).thenReturn(NumberOfProducts.of(5));
        when(shoppingCart.getProductNumbers()).thenReturn(NumberOfProducts.of(2));
        //when
        Money deliveryCost = deliveryCostCalculator.calculate(shoppingCart);
        //then
        Assert.assertEquals(Money.of(120), deliveryCost);
    }
}
