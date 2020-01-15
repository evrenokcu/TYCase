package shoppingcart.product;

import shoppingcart.shared.ddd.ValueObject;

//immutable category ddd value object
public class Category extends ValueObject<Category> {

    public static Category None = new Category("None");
    private String title;
    private Category parent;

    private Category(String title) {
        this.title = title;
    }

    private Category(String title, Category parent) {
        this.title = title;
        this.parent = parent;
    }

    public static Category of(String title) {
        return of(title, Category.None);
    }

    public static Category of(String title, Category category) {
        if (null == title || title.length() == 0 || title.length() > 15) {
            throw new IllegalArgumentException("Invalid category title");
        }
        if (null == category) {
            throw new IllegalArgumentException("Invalid parent category");
        }
        return new Category(title, category);
    }

    @Override
    protected boolean equalsCore(Category other) {
        if (!title.equals(other.title)) return false;
        return parent != null ? parent.equals(other.parent) : other.parent == null;
    }

    @Override
    protected int hashCodeCore() {
        int result = title.hashCode();
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        return result;
    }

    public String getTitle() {
        return title;
    }

    public Category getParent() {
        return parent;
    }
}
