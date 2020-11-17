package sdemirci.packagechallenge.data;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import sdemirci.packagechallenge.exceptions.ConstraintException;
import sdemirci.packagechallenge.exceptions.DeserializeException;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemDeserializerTest {
    @DataProvider(name = "invalidItemStrings")
    public static Object[][] invalidItemStrings() {
        return new Object[][]{
                {"1,53.38,"},
                {"1,53.38"},
                {"1,€45"},
                {"53.38,€45"},
                {"1,53.38,€45,2"}
        };
    }

    @DataProvider(name = "itemStringWithInvalidPrice")
    public static Object[][] itemStringWithInvalidPrice() {
        return new Object[][]{
                {"1,53.38,45"},
                {"1,53.38,$45"},
                {"1,53.38,45€"},
        };
    }

    @Test()
    public void shouldCreateItemWhenInputIsValid() throws DeserializeException, ConstraintException {
        String testItem = "1,53.38,\u20ac45";
        Item expectedItem = new Item(1, 5338, 45);

        ItemValidator mockItemValidator = mock(ItemValidator.class);
        WeightConverter mockWeightConverter = mock(WeightConverter.class);
        when(mockWeightConverter.convert("53.38")).thenReturn(5338);

        ItemDeserializer itemDeserializer = new ItemDeserializer(mockItemValidator, mockWeightConverter);
        Item actualItem = itemDeserializer.deserialize(testItem);
        Assert.assertEquals(actualItem, expectedItem);
    }

    @Test(expectedExceptions = DeserializeException.class, dataProvider = "invalidItemStrings")
    public void shouldThrowExceptionWhenInputIsNotValid(String testItem) throws DeserializeException {
        ItemValidator mockItemValidator = mock(ItemValidator.class);
        WeightConverter mockWeightConverter = mock(WeightConverter.class);

        ItemDeserializer itemDeserializer = new ItemDeserializer(mockItemValidator, mockWeightConverter);
        itemDeserializer.deserialize(testItem);
    }

    @Test(expectedExceptions = DeserializeException.class, dataProvider = "itemStringWithInvalidPrice")
    public void shouldThrowExceptionWhenPriceInputIsNotValid(String testItem) throws DeserializeException {
        ItemValidator mockItemValidator = mock(ItemValidator.class);
        WeightConverter mockWeightConverter = mock(WeightConverter.class);

        ItemDeserializer itemDeserializer = new ItemDeserializer(mockItemValidator, mockWeightConverter);
        itemDeserializer.deserialize(testItem);
    }

    @Test(expectedExceptions = DeserializeException.class)
    public void shouldThrowExceptionWhenWeightConverterThrow() throws DeserializeException, ConstraintException {
        String testItem = "1,53.3,\u20ac45";

        ItemValidator mockItemValidator = mock(ItemValidator.class);
        WeightConverter mockWeightConverter = mock(WeightConverter.class);

        when(mockWeightConverter.convert(anyString())).thenThrow(DeserializeException.class);
        ItemDeserializer itemDeserializer = new ItemDeserializer(mockItemValidator, mockWeightConverter);
        itemDeserializer.deserialize(testItem);
    }

    @Test(expectedExceptions = DeserializeException.class)
    public void shouldThrowExceptionWhenIndexNotNumber() throws DeserializeException, ConstraintException {
        String testItem = "anyString,53.38,\u20ac45";

        ItemValidator mockItemValidator = mock(ItemValidator.class);
        WeightConverter mockWeightConverter = mock(WeightConverter.class);

        when(mockWeightConverter.convert(anyString())).thenThrow(DeserializeException.class);
        ItemDeserializer itemDeserializer = new ItemDeserializer(mockItemValidator, mockWeightConverter);
        itemDeserializer.deserialize(testItem);
    }

    @Test(expectedExceptions = DeserializeException.class)
    public void shouldThrowExceptionWhenPriceNotNumber() throws DeserializeException, ConstraintException {
        String testItem = "1,53.38,\u20acfortyfive";

        ItemValidator mockItemValidator = mock(ItemValidator.class);
        WeightConverter mockWeightConverter = mock(WeightConverter.class);

        when(mockWeightConverter.convert(anyString())).thenThrow(DeserializeException.class);
        ItemDeserializer itemDeserializer = new ItemDeserializer(mockItemValidator, mockWeightConverter);
        itemDeserializer.deserialize(testItem);
    }

    @Test()
    public void shouldCallItemValidatorWhenInputIsValid() throws DeserializeException, ConstraintException {
        String testItem = "1,15.3,\u20ac34";

        ItemValidator mockItemValidator = mock(ItemValidator.class);
        WeightConverter mockWeightConverter = mock(WeightConverter.class);

        ItemDeserializer itemDeserializer = new ItemDeserializer(mockItemValidator, mockWeightConverter);
        itemDeserializer.deserialize(testItem);
        verify(mockItemValidator, times(1)).validate(any());
    }
}