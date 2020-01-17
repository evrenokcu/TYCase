package shoppingcart.shared;

import shoppingcart.shared.ddd.ValueObject;

public class NumberOfProducts extends ValueObject<NumberOfProducts> {

    public static final NumberOfProducts Zero = NumberOfProducts.of(0);
    private int value;

    private NumberOfProducts(int value) {
        this.value = value;
    }

    public static NumberOfProducts of(int numberOfProducts) {
        if (numberOfProducts < 0) {
            throw new IllegalArgumentException("number of products can not be negative");
        }
        if (0 == numberOfProducts) {
            return Zero;
        }
        return new NumberOfProducts(numberOfProducts);
    }


    public int getValue() {
        return value;
    }

    @Override
    protected boolean equalsCore(NumberOfProducts other) {
        return value == other.value;
    }

    @Override
    protected int hashCodeCore() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }

    public NumberOfProducts add(NumberOfProducts quantity) {
        return NumberOfProducts.of(this.value + quantity.value);
    }

    public boolean isGreaterThanOrEqual(NumberOfProducts numberOfProducts) {
        return this.getValue() >= numberOfProducts.getValue();
    }
}
