package sdemirci.packagechallenge;

import org.testng.annotations.Test;
import sdemirci.packagechallenge.exceptions.ApplicationArgumentException;
import sdemirci.packagechallenge.exceptions.DeserializeException;

public class RunApplicationTest {
    @Test
    public void shouldNotThrowExceptionWhenInputIsValid() throws DeserializeException, ApplicationArgumentException {
        String[] args = new String[] {"src/test/resources/successfulTestInput.txt"};

        RunApplication.main(args);
    }

    @Test(expectedExceptions = DeserializeException.class)
    public void shouldThrowExceptionWhenInputIsValid() throws DeserializeException, ApplicationArgumentException {
        String[] args = new String[] {"src/test/resources/failTestInput.txt"};

        RunApplication.main(args);
    }
}