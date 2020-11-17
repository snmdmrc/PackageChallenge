package sdemirci.packagechallenge.data;

import sdemirci.packagechallenge.exceptions.ConstraintException;

import static sdemirci.packagechallenge.data.Constants.MAX_ITEM_PRICE;

public class ItemValidator {
    public void validate(Item item) throws ConstraintException {
        if (item.getPrice() > MAX_ITEM_PRICE) {
            throw new ConstraintException("Item price cannot be higher than " + MAX_ITEM_PRICE);
        }
    }
}
