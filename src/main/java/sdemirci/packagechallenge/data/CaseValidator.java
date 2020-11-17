package sdemirci.packagechallenge.data;

import sdemirci.packagechallenge.exceptions.ConstraintException;

import static sdemirci.packagechallenge.data.Constants.MAX_ITEM_COUNT;

public class CaseValidator {
    public void validate(Case caseItem) throws ConstraintException {
        if (caseItem.getItems().size() > MAX_ITEM_COUNT) {
            throw new ConstraintException("Item count cannot be higher than " + MAX_ITEM_COUNT);
        }
    }
}
