package shoppingcart.discount;

import org.junit.Assert;
import org.junit.Test;
import shoppingcart.shared.Money;

public class DiscountTests {
    //Similar test like exception checking can be implemented
    //as done for Category and Money value objects

    @Test
    public void discountByAmountShouldReturnTheDiscount() {
        Assert.assertEquals(
                Money.of(10.2),
                Discount.ofAmount(10.2).calculate(Money.of(19.1)));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void discountAmountShouldNotExceedAmount() {
        Discount.ofAmount(10).calculate(Money.of(8));
    }

    @Test
    public void discountByRateShouldReturnTheCorrectPercentage() {
        Assert.assertEquals(
                Money.of(1.23),
                Discount.ofRate(10).calculate(Money.of(12.3))
        );

    }

}
