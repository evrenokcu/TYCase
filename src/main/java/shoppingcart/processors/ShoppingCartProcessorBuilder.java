package shoppingcart.processors;

public class ShoppingCartProcessorBuilder {

    private ShoppingCartProcessor firstProcessor = null;
    private ShoppingCartProcessor lastProcessor = null;


    public void add(ShoppingCartProcessor shoppingCartProcessor) {

        if (null == firstProcessor) {
            firstProcessor = shoppingCartProcessor;
        } else {
            lastProcessor.setNext(shoppingCartProcessor);
        }
        lastProcessor = shoppingCartProcessor;
    }

    public ShoppingCartProcessor build() {
        return firstProcessor;
    }

}
