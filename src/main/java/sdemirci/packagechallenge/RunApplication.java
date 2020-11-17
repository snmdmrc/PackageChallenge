package sdemirci.packagechallenge;

import sdemirci.packagechallenge.data.CaseDeserializer;
import sdemirci.packagechallenge.data.CaseValidator;
import sdemirci.packagechallenge.data.ItemDeserializer;
import sdemirci.packagechallenge.data.ItemValidator;
import sdemirci.packagechallenge.data.WeightConverter;
import sdemirci.packagechallenge.data.WeightValidator;
import sdemirci.packagechallenge.exceptions.ApplicationArgumentException;
import sdemirci.packagechallenge.exceptions.DeserializeException;

public class RunApplication {
    public static void main(String[] args) throws DeserializeException, ApplicationArgumentException {
        //Beans creation
        WeightValidator weightValidator = new WeightValidator();
        CaseValidator caseValidator = new CaseValidator();
        ItemValidator itemValidator = new ItemValidator();
        WeightConverter weightConverter = new WeightConverter(weightValidator);
        ItemDeserializer itemDeserializer = new ItemDeserializer(itemValidator, weightConverter);
        CaseDeserializer caseDeserializer = new CaseDeserializer(caseValidator, weightConverter, itemDeserializer);
        PackingSolver packingSolver = new PackingSolver();
        try {
            Application app = new Application(args, caseDeserializer, packingSolver);
            app.run();
        } catch (ApplicationArgumentException | DeserializeException exception) {
            System.out.println(exception.getMessage());
            throw exception;
        }
    }
}
