package shoppingcart.product;

import org.jetbrains.annotations.NotNull;
import shoppingcart.shared.NumberOfProducts;
import shoppingcart.shared.ddd.ValueObject;

public class ProductInShoppingCart extends ValueObject<ProductInShoppingCart> {
    private Product product;
    private NumberOfProducts quantity;

    private ProductInShoppingCart(Product product, NumberOfProducts quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    protected boolean equalsCore(ProductInShoppingCart other) {
        if (!product.equals(other.product)) return false;
        return quantity.equals(other.quantity);
    }

    @Override
    protected int hashCodeCore() {
        int result = product.hashCode();
        result = 31 * result + quantity.hashCode();
        return result;
    }

    public static ProductInShoppingCart of(@NotNull Product product, @NotNull NumberOfProducts quantity) {

        if (NumberOfProducts.Zero == quantity) {
            throw new IllegalArgumentException("product in cart should have quantity greater than zero");
        }
        return new ProductInShoppingCart(product, quantity);
    }
}
