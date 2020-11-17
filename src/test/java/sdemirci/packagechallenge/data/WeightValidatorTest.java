package sdemirci.packagechallenge.data;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import sdemirci.packagechallenge.exceptions.ConstraintException;

import static sdemirci.packagechallenge.data.Constants.MAX_PRECISION;
import static sdemirci.packagechallenge.data.Constants.MAX_WEIGHT;

public class WeightValidatorTest {
    @Test(dataProvider = "validWeightValues")
    public void shouldValidateWhenWeightIsValid(int validWeight) throws ConstraintException {
        WeightValidator validator = new WeightValidator();
        validator.validate(validWeight);
    }

    @Test(expectedExceptions = ConstraintException.class, dataProvider = "invalidWeightValues")
    public void shouldThrowExceptionWhenWeightIsNotValid(int notValidWeight) throws ConstraintException {
        WeightValidator validator = new WeightValidator();
        validator.validate(notValidWeight);
    }

    @DataProvider(name = "validWeightValues")
    public static Object[][] validWeightValues() {
        return new Object[][]{
                {0},
                {1},
                {10},
                {(int)Math.pow(MAX_WEIGHT, MAX_PRECISION)-1},
                {(int)Math.pow(MAX_WEIGHT, MAX_PRECISION)}
        };
    }

    @DataProvider(name = "invalidWeightValues")
    public static Object[][] invalidWeightValues() {
        return new Object[][]{
                {-10},
                {-1},
                {(int)Math.pow(MAX_WEIGHT, MAX_PRECISION)+1}
        };
    }
}