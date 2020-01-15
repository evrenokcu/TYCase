package shoppingcart.shared.ddd;

public abstract class ValueObject<T extends ValueObject<T>> {

    @Override
    public int hashCode() {
        return hashCodeCore();
    }


    @Override
    public boolean equals(Object obj) {
        T value = (T) obj;
        if (value == null) {
            return false;
        }
        return equalsCore(value);
    }

    protected abstract boolean equalsCore(T other);

    protected abstract int hashCodeCore();
}
