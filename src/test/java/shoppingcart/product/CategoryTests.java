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

    @Test
    public void subCategoryChecks() {
        //given
        Category technology = Category.of("technology");

        Category exported = Category.of("exported");
        Category food = Category.of("food", exported);
        Category nuts = Category.of("nuts", food);
        //when
        //then

        //category should be equal to itself
        Assert.assertTrue(nuts.isSubCategoryOrEquals(nuts));

        Assert.assertTrue(nuts.isSubCategoryOrEquals(food));
        Assert.assertTrue(nuts.isSubCategoryOrEquals(exported));

        Assert.assertFalse(exported.isSubCategoryOrEquals(nuts));
        Assert.assertFalse(nuts.isSubCategoryOrEquals(technology));
    }

}
