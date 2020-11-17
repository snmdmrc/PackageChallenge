package sdemirci.packagechallenge.data;

import sdemirci.packagechallenge.exceptions.ConstraintException;

import static sdemirci.packagechallenge.data.Constants.MAX_PRECISION;
import static sdemirci.packagechallenge.data.Constants.MAX_WEIGHT;

public class WeightValidator {
    public void validate(int weight) throws ConstraintException {
        if (weight < 0 || weight > Math.pow(MAX_WEIGHT, MAX_PRECISION)) {
            throw new ConstraintException("Total weight cannot be negative or higher than " + MAX_WEIGHT);
        }
    }
}
