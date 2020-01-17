package shoppingcart.processors;

import shoppingcart.cart.ShoppingCart;
import shoppingcart.cart.ShoppingCartItem;
import shoppingcart.shared.Money;

public class PrintProcessor extends ShoppingCartProcessor {
    String format = "%-15s %-15s %8s %14s %14s %37s %15s%n";

    @Override
    protected void beforeProcess(ShoppingCart shoppingCart) {

        System.out.format(format,
                "Category",
                "Product",
                "Quantity",
                "Unit Price",
                "Total Price",
                "Total Discount(coupon, campaign)",
                "Line Total"
        );
    }

    @Override
    protected void process(ShoppingCartItem shoppingCartItem) {
        System.out.format(format,
                shoppingCartItem.getProductInShoppingCart().getProduct().getCategory(),
                shoppingCartItem.getProductInShoppingCart().getProduct(),
                shoppingCartItem.getProductInShoppingCart().getQuantity(),
                shoppingCartItem.getProductInShoppingCart().getProduct().getPrice(),
                shoppingCartItem.getProductInShoppingCart().getTotalPrice(),
                String.format("%s (%s, %s)", shoppingCartItem.getTotalDiscount(), shoppingCartItem.getCouponDiscount(), shoppingCartItem.getCampaignDiscount()),
                shoppingCartItem.getTotalPrice().deduct(shoppingCartItem.getTotalDiscount())
        );
    }

    @Override
    protected void afterProcess(ShoppingCart shoppingCart) {
        System.out.format("%-25s %25s%n", "Total:", shoppingCart.getTotalAmount());
        Money totalDiscount = shoppingCart.getCouponDiscount().add(shoppingCart.getCampaignDiscount());
        System.out.format("%-25s %25s%n", "Discount:", String.format("%s(%s,%s)", totalDiscount, shoppingCart.getCouponDiscount(), shoppingCart.getCampaignDiscount()));
        System.out.format("%-25s %25s%n", "Amount with Discount", shoppingCart.getTotalAmountAfterDiscount());
        System.out.format("%-25s %25s%n", "Delivery Cost:", shoppingCart.getDeliveryCost());
        System.out.println("...................................................");
        System.out.format("%-25s %25s%n", "Grand Total:", shoppingCart.getTotalAmountAfterDiscount().add(shoppingCart.getDeliveryCost()));
    }
}
