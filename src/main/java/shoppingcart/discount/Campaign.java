package shoppingcart.discount;

import org.jetbrains.annotations.NotNull;
import shoppingcart.product.Category;
import shoppingcart.shared.NumberOfProducts;
import shoppingcart.shared.ddd.ValueObject;

public class Campaign extends ValueObject<Campaign> {
    private Category category;
    private NumberOfProducts numberOfProducts;
    private Discount discount;

    private Campaign(Category category, NumberOfProducts numberOfProducts, Discount discount) {
        this.category = category;
        this.numberOfProducts = numberOfProducts;
        this.discount = discount;
    }

    public static Campaign of(@NotNull Category category, @NotNull NumberOfProducts numberOfProducts, @NotNull Discount discount) {
        return new Campaign(category, numberOfProducts, discount);
    }

    public Category getCategory() {
        return category;
    }

    public NumberOfProducts getNumberOfProducts() {
        return numberOfProducts;
    }

    public Discount getDiscount() {
        return discount;
    }


    @Override
    protected boolean equalsCore(Campaign other) {
        if (!category.equals(other.category)) return false;
        if (!numberOfProducts.equals(other.numberOfProducts)) return false;
        return discount.equals(other.discount);
    }

    @Override
    protected int hashCodeCore() {
        int result = category.hashCode();
        result = 31 * result + numberOfProducts.hashCode();
        result = 31 * result + discount.hashCode();
        return result;
    }
}
