package shoppingcart.shared;

import org.junit.Assert;
import org.junit.Test;

public class NumberOfProductsTest {
    @Test
    public void zeroNumberOfProductShouldReturnZeroObjectInstance() {
        Assert.assertEquals(NumberOfProducts.Zero, NumberOfProducts.of(0));
        //reference equals
        Assert.assertTrue(NumberOfProducts.Zero == NumberOfProducts.of(0));
    }

    @Test
    public void addMethodShouldSumToTheQuantity() {
        Assert.assertEquals(NumberOfProducts.of(5), NumberOfProducts.of(2).add(NumberOfProducts.of(3)));
    }

    @Test
    public void greaterThanOrEqualsTest()
    {
        NumberOfProducts numberOfProducts = NumberOfProducts.of(10);

        NumberOfProducts bigger = NumberOfProducts.of(11);
        NumberOfProducts smaller = NumberOfProducts.of(8);
        NumberOfProducts equal = NumberOfProducts.of(10);

        Assert.assertTrue(numberOfProducts.isGreaterThanOrEqual(smaller));
        Assert.assertTrue(numberOfProducts.isGreaterThanOrEqual(equal));
        Assert.assertFalse(numberOfProducts.isGreaterThanOrEqual(bigger));

    }
}
