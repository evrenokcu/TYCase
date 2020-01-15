package shoppingcart.processors;

import shoppingcart.cart.ShoppingCart;
import shoppingcart.cart.ShoppingCartItem;
import shoppingcart.shared.Money;

import javax.swing.*;
import java.util.Comparator;

public class PrintProcessor extends ShoppingCartProcessor {
    String format = "%-15s %-15s %8s %14s %14s %37s %n";

    @Override
    protected void beforeProcess(ShoppingCart shoppingCart) {

        System.out.format(format,
                "Category",
                "Product",
                "Quantity",
                "Unit Price",
                "Total Price",
                "Total Discount(coupon, campaign)"

        );

    }

    @Override
    Comparator<ShoppingCartItem> getShoppingCartItemComparator() {
        Comparator<ShoppingCartItem> comparator = Comparator.comparing(item -> item.getProductInShoppingCart().getProduct().getCategory().getTitle());
        return comparator;
    }

    @Override
    protected void process(ShoppingCartItem shoppingCartItem) {
        System.out.format(format,
                shoppingCartItem.getProductInShoppingCart().getProduct().getCategory(),
                shoppingCartItem.getProductInShoppingCart().getProduct(),
                shoppingCartItem.getProductInShoppingCart().getQuantity(),
                shoppingCartItem.getProductInShoppingCart().getProduct().getPrice(),
                shoppingCartItem.getProductInShoppingCart().getTotalPrice(),
                String.format("%s (%s, %s)", shoppingCartItem.getTotalDiscount(), shoppingCartItem.getCouponDiscount(), shoppingCartItem.getCampaignDiscount())
        );
    }

    @Override
    protected void afterProcess(ShoppingCart shoppingCart) {
        System.out.format("%-25s %18s%n", "Total:", shoppingCart.getTotalAmount());
        Money totalDiscount = shoppingCart.getCouponDiscount().add(shoppingCart.getCampaignDiscount());
//        if (!Money.Zero.equals(totalDiscount)) {
        System.out.format("%-25s %18s%n", "Discount:", String.format("%s(%s,%s)", totalDiscount, shoppingCart.getCampaignDiscount(), shoppingCart.getCouponDiscount()));
        System.out.format("%-25s %18s%n", "Amount with Discount", shoppingCart.getTotalAmountAfterDiscount());
//        }
        System.out.format("%-25s %18s%n", "Delivery Cost:", shoppingCart.getDeliveryCost());
        System.out.format("%-25s %18s%n", "Grand Total:", shoppingCart.getTotalAmountAfterDiscount().add(shoppingCart.getDeliveryCost()));

    }
}
