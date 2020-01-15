package shoppingcart.product;

import org.junit.Assert;
import org.junit.Test;

public class CategoryTests {

    @Test(expected = IllegalArgumentException.class)
    public void nullTitleShouldThrowException() {
        Category.of(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void maxSizeLimitExceedsShouldThrowException() {
        Category.of("1234567890123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullParentShouldThrowException() {
        Category.of("nuts", null);
    }

    @Test
    public void categoryWithoutParentShouldHaveParentCategoryNone() {
        Assert.assertEquals(Category.None, Category.of("nuts").getParent());
    }


}
