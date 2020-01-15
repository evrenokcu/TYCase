package shoppingcart.shared;

import shoppingcart.shared.ddd.ValueObject;

public class Money extends ValueObject<Money> {

    private double amount;
    public static final Money Zero = Money.of(0);

    private Money(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public static Money of(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Money amount can not be negative");
        }
        return new Money(amount);
    }

    protected boolean equalsCore(Money other) {
        return amount == other.amount;
    }

    protected int hashCodeCore() {
        long temp = Double.doubleToLongBits(amount);
        int result = 31 * (int) (temp ^ (temp >>> 32));
        return result;
    }


}
