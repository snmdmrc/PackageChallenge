package sdemirci.packagechallenge.data;

import org.testng.Assert;
import org.testng.annotations.Test;
import sdemirci.packagechallenge.exceptions.ConstraintException;
import sdemirci.packagechallenge.exceptions.DeserializeException;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CaseDeserializerTest {

    //Todo: Multiplex valid input test data with DataProvider
    @Test()
    public void shouldCreateCasesWhenInputIsValid() throws DeserializeException, ConstraintException {
        String testLine = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
        Item expectedItem1 = new Item(1, 5338, 45);
        Item expectedItem2 = new Item(2, 8862, 98);
        Item expectedItem3 = new Item(3, 7848, 3);
        Item expectedItem4 = new Item(4, 7230, 76);
        Item expectedItem5 = new Item(5, 3018, 9);
        Item expectedItem6 = new Item(6, 4634, 48);
        Case expectedCase = new Case(81, Arrays.asList(expectedItem1, expectedItem2, expectedItem3, expectedItem4, expectedItem5, expectedItem6));

        CaseValidator mockCaseValidator = mock(CaseValidator.class);
        WeightConverter mockWeightConverter = mock(WeightConverter.class);
        ItemDeserializer mockItemDeserializer = mock(ItemDeserializer.class);

        when(mockWeightConverter.convert(anyString())).thenReturn(81);
        when(mockItemDeserializer.deserialize(anyString()))
                .thenReturn(expectedItem1)
                .thenReturn(expectedItem2)
                .thenReturn(expectedItem3)
                .thenReturn(expectedItem4)
                .thenReturn(expectedItem5)
                .thenReturn(expectedItem6)
                .thenReturn(expectedItem1)
                .thenReturn(expectedItem2)
                .thenReturn(expectedItem3)
                .thenReturn(expectedItem4)
                .thenReturn(expectedItem5)
                .thenReturn(expectedItem6);

        CaseDeserializer caseDeserializer = new CaseDeserializer(mockCaseValidator, mockWeightConverter, mockItemDeserializer);
        Case actualCase = caseDeserializer.deserialize(testLine);

        Assert.assertEquals(actualCase, expectedCase);
        verify(mockItemDeserializer, times(1)).deserialize("1,53.38,€45");
    }

    //Todo: Multiplex invalid input test data with DataProvider
    @Test(expectedExceptions = DeserializeException.class)
    public void shouldThrowExceptionWhenInputIsNotValid() throws DeserializeException, ConstraintException {
        String testLine = "81:(1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";

        CaseValidator mockCaseValidator = mock(CaseValidator.class);
        WeightConverter mockWeightConverter = mock(WeightConverter.class);
        ItemDeserializer mockItemDeserializer = mock(ItemDeserializer.class);

        CaseDeserializer caseDeserializer = new CaseDeserializer(mockCaseValidator, mockWeightConverter, mockItemDeserializer);
        caseDeserializer.deserialize(testLine);
    }

    @Test(expectedExceptions = DeserializeException.class)
    public void shouldThrowExceptionWhenItemDeserializerThrowsException() throws DeserializeException, ConstraintException {
        String testLine = "81 : (1,53.38,45) (2,88.62,98) (3,78.48,3) (4,72.30,76) (5,30.18,9) (6,46.34,48)";

        CaseValidator mockCaseValidator = mock(CaseValidator.class);
        WeightConverter mockWeightConverter = mock(WeightConverter.class);
        ItemDeserializer mockItemDeserializer = mock(ItemDeserializer.class);

        when(mockItemDeserializer.deserialize(anyString())).thenThrow(DeserializeException.class);

        CaseDeserializer caseDeserializer = new CaseDeserializer(mockCaseValidator, mockWeightConverter, mockItemDeserializer);
        caseDeserializer.deserialize(testLine);
    }

    @Test()
    public void shouldCallCaseValidatorWhenInputIsValid() throws DeserializeException, ConstraintException {
        String testLine = "8 : (1,15.3,€34)";

        CaseValidator mockCaseValidator = mock(CaseValidator.class);
        WeightConverter mockWeightConverter = mock(WeightConverter.class);
        ItemDeserializer mockItemDeserializer = mock(ItemDeserializer.class);

        when(mockItemDeserializer.deserialize("1,15.3,€34")).thenReturn(new Item(1,1530, 34));

        CaseDeserializer caseDeserializer = new CaseDeserializer(mockCaseValidator, mockWeightConverter, mockItemDeserializer);
        caseDeserializer.deserialize(testLine);

        verify(mockCaseValidator,times(1)).validate(any());
    }
}