package shoppingcart.discount.calculators;

import shoppingcart.shared.Money;

import java.util.HashMap;
import java.util.Map;

public class DiscountResult {
    private Money totalDiscount = Money.Zero;
    private Map<String, Money> itemDiscounts = new HashMap<>();

    public DiscountResult() {
    }

    public Money getTotalDiscount() {
        return totalDiscount;
    }

    public Map<String, Money> getItemDiscounts() {
        return itemDiscounts;
    }

    public void setTotalDiscount(Money totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public void setItemDiscounts(Map<String, Money> itemDiscounts) {
        this.itemDiscounts = itemDiscounts;
    }

    public void applyDiscountItem(String title, Money discount) {
        this.itemDiscounts.compute(title,
                (key, value) -> (value == null)
                        ? discount
                        : discount);
    }

    public void addToTotalDiscount(Money discount) {
        this.totalDiscount = this.totalDiscount.add(discount);
    }
}
