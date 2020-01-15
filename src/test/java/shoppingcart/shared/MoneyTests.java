package shoppingcart.shared;

import org.junit.Assert;
import org.junit.Test;

public class MoneyTests {
    @Test(expected = IllegalArgumentException.class)
    public void moneyAmountCanNotBeNegative() {
        Money.of(-2);
    }

    @Test
    public void differentMoneySameAmountsShouldBeEqual() {
        Assert.assertEquals(Money.of(1.2), Money.of(1.2));
    }
    @Test
    public void differentMoneySameAmountShouldHaveSameHascode()
    {
        Assert.assertEquals(Money.of(3.1).hashCode(), Money.of(3.1).hashCodeCore());
    }


}
