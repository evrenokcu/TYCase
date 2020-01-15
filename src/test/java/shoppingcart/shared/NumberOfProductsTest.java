package shoppingcart.shared;

import org.junit.Assert;
import org.junit.Test;

public class NumberOfProductsTest {
    @Test
    public void ZeroNumberOfProductShouldReturnZeroObjectInstance() {
        Assert.assertEquals(NumberOfProducts.Zero, NumberOfProducts.of(0));
        //reference equals
        Assert.assertTrue(NumberOfProducts.Zero == NumberOfProducts.of(0));
    }

    @Test
    public void addMethodShouldSumToTheQuantity() {
        Assert.assertEquals(NumberOfProducts.of(5), NumberOfProducts.of(2).add(NumberOfProducts.of(3)));
    }
}
