package shoppingcart.product;

import shoppingcart.shared.Money;
import shoppingcart.shared.ddd.ValueObject;

public class Product extends ValueObject<Product> {
    private String title;
    private Money price;
    private Category category;

    @Override
    protected boolean equalsCore(Product other) {
        if (!title.equals(other.title)) return false;
        if (!price.equals(other.price)) return false;
        return category.equals(other.category);
    }

    @Override
    protected int hashCodeCore() {
        int result = title.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + category.hashCode();
        return result;
    }


    private Product(String title, Money price, Category category) {
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public Money getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public static Product of(String title, Money price, Category category) {
        if (null == title || title.length() == 0 || title.length() > 15) {
            throw new IllegalArgumentException("Invalid product title");
        }
        if (null == category) {
            throw new IllegalArgumentException("Invalid  category");
        }
        if (null == price) {
            throw new IllegalArgumentException("Invalid pricee");
        }
        return new Product(title, price, category);
    }
}
