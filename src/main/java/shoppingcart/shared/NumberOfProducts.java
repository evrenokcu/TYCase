package shoppingcart.shared;

import shoppingcart.shared.ddd.ValueObject;

public class NumberOfProducts extends ValueObject<NumberOfProducts> {
    private int numberOfProducts;
    public static final NumberOfProducts Zero = NumberOfProducts.of(0);

    private NumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
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

    @Override
    protected boolean equalsCore(NumberOfProducts other) {
        return numberOfProducts == other.numberOfProducts;
    }

    @Override
    protected int hashCodeCore() {
        return numberOfProducts;
    }

    public NumberOfProducts add(NumberOfProducts quantity) {
        return NumberOfProducts.of(this.numberOfProducts+quantity.numberOfProducts);
    }
}
