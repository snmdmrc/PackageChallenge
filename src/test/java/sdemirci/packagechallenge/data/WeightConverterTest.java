package sdemirci.packagechallenge.data;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import sdemirci.packagechallenge.exceptions.ConstraintException;
import sdemirci.packagechallenge.exceptions.DeserializeException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WeightConverterTest {

    @DataProvider(name = "validWeightStrings")
    public static Object[][] validWeightStrings() {
        return new Object[][]{
                {"53.38", 5338},
                {"10", 1000},
                {"0.11", 11},
                {"53.3", 5330}
        };
    }

    @DataProvider(name = "invalidWeightStrings")
    public static Object[][] invalidWeightStrings() {
        return new Object[][]{
                {"53.388"}
        };
    }

    @Test(dataProvider = "validWeightStrings")
    public void shouldReturnWeightWhenWeightInputIsValid(String testWeight, int expectedWeight) throws DeserializeException, ConstraintException {
        WeightValidator mockWeightValidator = mock(WeightValidator.class);

        WeightConverter weightConverter = new WeightConverter(mockWeightValidator);
        int actualWeight = weightConverter.convert(testWeight);

        Assert.assertEquals(actualWeight, expectedWeight);
    }

    @Test(expectedExceptions = DeserializeException.class, dataProvider = "invalidWeightStrings")
    public void shouldThrowExceptionWhenWeightInputIsNotValid(String testWeight) throws DeserializeException, ConstraintException {
        WeightValidator mockWeightValidator = mock(WeightValidator.class);

        WeightConverter weightConverter = new WeightConverter(mockWeightValidator);
        weightConverter.convert(testWeight);
    }

    @Test(expectedExceptions = ConstraintException.class)
    public void shouldThrowExceptionWhenWeightValidatorThrows() throws DeserializeException, ConstraintException {
        String testWeight = "101";
        WeightValidator mockWeightValidator = mock(WeightValidator.class);
        doThrow(ConstraintException.class).when(mockWeightValidator).validate(10100);

        WeightConverter weightConverter = new WeightConverter(mockWeightValidator);
        weightConverter.convert(testWeight);
    }

    @Test()
    public void shouldCallWeightValidatorWhenWeightInputIsValid() throws DeserializeException, ConstraintException {
        String testWeight = "100";

        WeightValidator mockWeightValidator = mock(WeightValidator.class);

        WeightConverter weightConverter = new WeightConverter(mockWeightValidator);
        weightConverter.convert(testWeight);

        verify(mockWeightValidator, times(1)).validate(10000);

    }
}