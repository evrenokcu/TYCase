package shoppingcart.shared;

import shoppingcart.shared.ddd.ValueObject;

import java.math.BigDecimal;

public class Money extends ValueObject<Money> {

    private BigDecimal amount;
    public static final Money Zero = Money.of(BigDecimal.valueOf(0));

    private Money(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public static Money of(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(0)) == -1) {
            throw new IllegalArgumentException("Money amount can not be negative");
        }
        if (amount.equals(0)) {
            return Zero;
        }
        return new Money(amount);
    }

    public static Money of(double amount) {
        return of(BigDecimal.valueOf(amount));
    }

    protected boolean equalsCore(Money other) {
        return amount.equals(other.amount);
    }

    protected int hashCodeCore() {
        int result = 31 * amount.hashCode();
        return result;
    }

    public Money percentageOf(BigDecimal percentage) {
        if (percentage.compareTo(BigDecimal.valueOf(100)) == 1 || percentage.compareTo(BigDecimal.valueOf(0)) == -1) {
            throw new IllegalArgumentException("Invalid Percentage");
        }
        return Money.of(amount.multiply(percentage).divide(BigDecimal.valueOf(100)));

    }

    public Money percentageOf(double percentage) {
        return percentageOf(BigDecimal.valueOf(percentage));

    }


    public boolean isGreaterThan(Money money) {
        return this.amount.compareTo(money.amount) == 1;
    }

    public Money add(Money amount) {
        return Money.of(this.amount.add(amount.amount));
    }

    public Money multiply(NumberOfProducts quantity) {
        return Money.of(this.amount.multiply(BigDecimal.valueOf(quantity.getNumberOfProducts())));
    }
}
