package sdemirci.packagechallenge;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import sdemirci.packagechallenge.data.Case;
import sdemirci.packagechallenge.data.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PackingSolverTest {

    @DataProvider(name = "validCases")
    public static Object[][] validCases() {
        List<Integer>[] solutions = new List[]{
                Collections.emptyList(),
                Collections.singletonList(4),
                Arrays.asList(2, 7),
                Arrays.asList(8, 9)
        };

        Case[] cases = new Case[]{
                new Case(800, Collections.singletonList(new Item(1, 1530, 34))),
                new Case(8100, Arrays.asList(
                        new Item(1, 5338, 45),
                        new Item(2, 8862, 98),
                        new Item(3, 7848, 3),
                        new Item(4, 7230, 76),
                        new Item(5, 3018, 9),
                        new Item(6, 4634, 48))
                ),
                new Case(7500, Arrays.asList(
                        new Item(1, 8531, 29),
                        new Item(2, 1455, 74),
                        new Item(3, 398, 16),
                        new Item(4, 2624, 55),
                        new Item(5, 6369, 52),
                        new Item(6, 7625, 75),
                        new Item(7, 6002, 74),
                        new Item(8, 9318, 35),
                        new Item(9, 8995, 78))
                ),
                new Case(7500, Arrays.asList(
                        new Item(1, 9072, 13),
                        new Item(2, 3380, 40),
                        new Item(3, 4315, 10),
                        new Item(4, 3797, 16),
                        new Item(5, 4681, 36),
                        new Item(6, 4877, 79),
                        new Item(7, 8180, 75),
                        new Item(8, 1936, 79),
                        new Item(9, 676, 64))
                ),
        };
        return new Object[][]{
                {cases[0], solutions[0]},
                {cases[1], solutions[1]},
        };
    }

    @Test
    public void shouldReturnEmptySetWhenItemCountIsZero() {
        Case testCase = new Case(10, new ArrayList<Item>() {
        });

        PackingSolver packingSolver = new PackingSolver();
        List<Integer> solutionSet = packingSolver.solve(testCase);
        Assert.assertEquals(solutionSet.size(), 0);
    }

    @Test
    public void shouldReturnEmptySetWhenTotalWeightIsZero() {
        Case testCase = new Case(0, Collections.singletonList(new Item(1, 10, 25)));

        PackingSolver packingSolver = new PackingSolver();
        List<Integer> solutionSet = packingSolver.solve(testCase);
        Assert.assertEquals(solutionSet.size(), 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenTotalWeightIsNegative() {
        Case testCase = new Case(-1, Collections.singletonList(new Item(1, 10, 25)));

        PackingSolver packingSolver = new PackingSolver();
        packingSolver.solve(testCase);
    }

    @Test(dataProvider = "validCases")
    public void shouldReturnSolutionSetWhenValidCasesProvided(Case testCase, List<Integer> expectedSolution) {
        PackingSolver packingSolver = new PackingSolver();
        List<Integer> actualSolution = packingSolver.solve(testCase);
        Assert.assertEquals(actualSolution, expectedSolution);
    }

    @Test
    public void shouldSelectLighterSetWhenCaseWithTwoSolutionProvided() {
        Case testCase = new Case(900, Arrays.asList(
                new Item(1, 550, 5),
                new Item(2, 350, 5),
                new Item(3, 899, 10)
        ));

        PackingSolver packingSolver = new PackingSolver();
        List<Integer> actualSolution = packingSolver.solve(testCase);

        Assert.assertEquals(actualSolution, Collections.singletonList(3));
    }

}
