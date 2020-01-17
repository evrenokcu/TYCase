package shoppingcart.processors;

import org.junit.Test;
import org.mockito.Mockito;
import shoppingcart.cart.ShoppingCart;
import shoppingcart.shared.NumberOfProducts;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeliveryCostProcessorTest {
    @Test
    public void verifyDeliveryCostIsAppliedToTheShoppingCart() {

        //given

        ShoppingCart shoppingCart = Mockito.mock(ShoppingCart.class);
        DeliveryCostProcessor deliveryCostProcessor = new DeliveryCostProcessor();
        when(shoppingCart.getProductNumbers()).thenReturn(NumberOfProducts.of(3));
        when(shoppingCart.getNumberOfDeliveries()).thenReturn(NumberOfProducts.of(4));

        //when
        deliveryCostProcessor.process(shoppingCart);
        //then
        verify(shoppingCart).setDeliveryCost(any());
    }
}
