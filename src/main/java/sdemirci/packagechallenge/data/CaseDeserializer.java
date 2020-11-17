package sdemirci.packagechallenge.data;

import org.apache.commons.lang3.StringUtils;
import sdemirci.packagechallenge.exceptions.ConstraintException;
import sdemirci.packagechallenge.exceptions.DeserializeException;

import java.util.ArrayList;
import java.util.List;

import static sdemirci.packagechallenge.data.Constants.ITEM_ENDING;
import static sdemirci.packagechallenge.data.Constants.ITEM_STARTING;
import static sdemirci.packagechallenge.data.Constants.PACKAGE_WEIGHT_SEPARATOR;

public class CaseDeserializer {
    private final CaseValidator caseValidator;
    private final WeightConverter weightConverter;
    private final ItemDeserializer itemDeserializer;

    public CaseDeserializer(CaseValidator caseValidator, WeightConverter weightConverter, ItemDeserializer itemDeserializer) {
        this.caseValidator = caseValidator;
        this.weightConverter = weightConverter;
        this.itemDeserializer = itemDeserializer;
    }

    public Case deserialize(String line) throws DeserializeException, ConstraintException {
        List<Item> items = new ArrayList<>();
        String[] splitLine = line.split(PACKAGE_WEIGHT_SEPARATOR);
        if (splitLine.length != 2) {
            throw new DeserializeException("Given line is malformed. Maximum weight and item must be separated with " + PACKAGE_WEIGHT_SEPARATOR);
        }

        String[] itemsInString = StringUtils.substringsBetween(splitLine[1], ITEM_STARTING, ITEM_ENDING);
        for (String itemInString : itemsInString) {
            items.add(itemDeserializer.deserialize(itemInString));
        }

        try {
            int weight = weightConverter.convert(splitLine[0].trim());
            Case createdCase = new Case(weight, items);
            caseValidator.validate(createdCase);

            return createdCase;
        } catch (NumberFormatException numberFormatException) {
            throw new DeserializeException("Failed to deserialize line '" + line + "' with error " + numberFormatException.getMessage(), numberFormatException);
        }
    }
}
