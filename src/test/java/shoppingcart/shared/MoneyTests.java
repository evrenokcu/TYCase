package shoppingcart.shared;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class MoneyTests {
    @Test(expected = IllegalArgumentException.class)
    public void moneyAmountCanNotBeNegative() {
        Money.of(BigDecimal.valueOf(-2));
    }

    @Test
    public void differentMoneySameAmountsShouldBeEqual() {
        Assert.assertEquals(Money.of(1.2), Money.of(1.2));
    }

    @Test
    public void differentMoneySameAmountShouldHaveSameHashcode() {
        Assert.assertEquals(Money.of(3.1).hashCode(), Money.of(3.1).hashCodeCore());
    }

    @Test
    public void percentageOf() {
        Assert.assertEquals(Money.of(6.25), Money.of(50).percentageOf(12.5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativePercentageShouldThrowException() {

        Money.of(10).percentageOf(-23);
    }

    @Test(expected = IllegalArgumentException.class)
    public void percentageAbove100ShouldThrowException() {
        Money.of(10).percentageOf(100.1);
    }

    @Test
    public void isGreaterThan() {
        Assert.assertTrue(Money.of(12.2).isGreaterThan(Money.of(12.1)));
        Assert.assertFalse(Money.of(1).isGreaterThan(Money.of(2)));
        Assert.assertFalse(Money.of(10).isGreaterThan(Money.of(10)));
    }

    @Test
    public void amountShouldAddCorrectly() {
        Money expected = Money.of(11.3);
        Money actual = Money.of(10.1000).add(Money.of(1.2000));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void multiplyShouldReflectToTheAmount() {
        //given
        Money money = Money.of(1.3);
        //when
        Money newAmount = money.multiply(NumberOfProducts.of(4));
        //then
        Assert.assertEquals(Money.of(5.2), newAmount);

    }

    @Test
    public void validateGreaterThanOrEquals() {
        Money money = Money.of(10.1);

        Money bigger = Money.of(11.2);
        Money smaller = Money.of(8.3);
        Money equal = Money.of(10.1);

        Assert.assertTrue(money.isGreaterThanOrEqual(smaller));
        Assert.assertTrue(money.isGreaterThanOrEqual(equal));
        Assert.assertFalse(money.isGreaterThanOrEqual(bigger));
    }

    @Test
    public void validateDeduction() {
        //given
        Money amount = Money.of(1.2);
        Money amountToDeduct = Money.of(0.1);
        //when
        Money result = amount.deduct(amountToDeduct);
        //then
        Assert.assertEquals(Money.of(1.1), result);


    }
//    @Test
//    public void reason of conversion from doubleToBigDecimal() {

//        BigDecimal d1 = BigDecimal.valueOf(10.1000);
//        BigDecimal d2 = BigDecimal.valueOf(1.2000);
//
//        BigDecimal d3 = d1.add(d2);
//        Assert.assertEquals(BigDecimal.valueOf(11.3), d3);
//
//    }


}
