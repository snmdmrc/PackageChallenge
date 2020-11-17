package sdemirci.packagechallenge.data;

import org.testng.annotations.Test;
import sdemirci.packagechallenge.exceptions.ConstraintException;

import static sdemirci.packagechallenge.data.Constants.MAX_ITEM_PRICE;


public class ItemValidatorTest {
    @Test
    public void shouldValidateWhenItemPriceNotHigherMaxPrice() throws ConstraintException {
        Item testItem = new Item(1, 10, 5);

        ItemValidator itemValidator = new ItemValidator();
        itemValidator.validate(testItem);
    }

    @Test(expectedExceptions = ConstraintException.class)
    public void shouldThrowExceptionWhenItemPriceHigherMaxPrice() throws ConstraintException {
        Item testItem = new Item(1, 10, MAX_ITEM_PRICE+1);

        ItemValidator itemValidator = new ItemValidator();
        itemValidator.validate(testItem);
    }
}