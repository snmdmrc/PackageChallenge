package sdemirci.packagechallenge.data;

import org.testng.annotations.Test;
import sdemirci.packagechallenge.exceptions.ConstraintException;

import java.util.Arrays;
import java.util.Collections;

public class CaseValidatorTest {
    @Test
    public void shouldValidateWhenItemCountIsNotHigherThenDefined() throws ConstraintException {
        Case testCase = new Case(10, Collections.singletonList(new Item(1, 10, 5)));

        CaseValidator caseValidator = new CaseValidator();
        caseValidator.validate(testCase);
    }

    @Test(expectedExceptions = ConstraintException.class)
    public void shouldThrowExceptionWhenItemCountIsHigherThenDefined() throws ConstraintException {
        Case testCase = new Case(10, Arrays.asList(
                new Item(1, 10, 5),
                new Item(2, 10, 5),
                new Item(3, 10, 5),
                new Item(4, 10, 5),
                new Item(5, 10, 5),
                new Item(6, 10, 5),
                new Item(7, 10, 5),
                new Item(8, 10, 5),
                new Item(9, 10, 5),
                new Item(10, 10, 5),
                new Item(11, 10, 5),
                new Item(12, 10, 5),
                new Item(13, 10, 5),
                new Item(14, 10, 5),
                new Item(15, 10, 5),
                new Item(16, 10, 5)));

        CaseValidator caseValidator = new CaseValidator();
        caseValidator.validate(testCase);
    }
}