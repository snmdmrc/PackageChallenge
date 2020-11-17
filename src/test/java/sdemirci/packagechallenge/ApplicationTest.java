package sdemirci.packagechallenge;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import sdemirci.packagechallenge.data.CaseDeserializer;
import sdemirci.packagechallenge.exceptions.ApplicationArgumentException;

import static org.mockito.Mockito.mock;

public class ApplicationTest {

    @DataProvider(name = "argumentTestValues")
    public static Object[][] unexpectedArguments() {
        return new Object[][]{
                {"argument1", "argument2"},
                {"argument1", "argument2", "argument3"}
        };
    }

    @Test(expectedExceptions = ApplicationArgumentException.class)
    public void shouldThrowExceptionWhenArgumentsIsNull() throws ApplicationArgumentException {
        CaseDeserializer mockCaseDeserializer = mock(CaseDeserializer.class);
        PackingSolver mockPackingSolver = mock(PackingSolver.class);

        String[] args = null;
        new Application(args, mockCaseDeserializer, mockPackingSolver);
    }

    @Test(expectedExceptions = ApplicationArgumentException.class)
    public void shouldThrowExceptionWhenArgumentsIsEmpty() throws ApplicationArgumentException {
        CaseDeserializer mockCaseDeserializer = mock(CaseDeserializer.class);
        PackingSolver mockPackingSolver = mock(PackingSolver.class);

        String[] args = new String[0];
        new Application(args, mockCaseDeserializer, mockPackingSolver);
    }

    @Test(expectedExceptions = ApplicationArgumentException.class, dataProvider = "argumentTestValues")
    public void shouldThrowExceptionWhenArgumentsCountMoreThenOne(String[] args) throws ApplicationArgumentException {
        CaseDeserializer mockCaseDeserializer = mock(CaseDeserializer.class);
        PackingSolver mockPackingSolver = mock(PackingSolver.class);

        new Application(args, mockCaseDeserializer, mockPackingSolver);
    }

    @Test(expectedExceptions = ApplicationArgumentException.class)
    public void shouldThrowExceptionWhenFileNotExists() throws ApplicationArgumentException {
        CaseDeserializer mockCaseDeserializer = mock(CaseDeserializer.class);
        PackingSolver mockPackingSolver = mock(PackingSolver.class);

        String[] args = new String[]{"C:\\Users\\currentuser\\tmp\\testfile.txt"};
        new Application(args, mockCaseDeserializer, mockPackingSolver);
    }
}