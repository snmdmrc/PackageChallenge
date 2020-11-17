package sdemirci.packagechallenge.data;

import org.apache.commons.lang3.StringUtils;
import sdemirci.packagechallenge.exceptions.ConstraintException;
import sdemirci.packagechallenge.exceptions.DeserializeException;

import static sdemirci.packagechallenge.data.Constants.MAX_PRECISION;

public class WeightConverter {
    private final WeightValidator weightValidator;

    public WeightConverter(WeightValidator weightValidator) {
        this.weightValidator = weightValidator;
    }

    public int convert(String weight) throws DeserializeException, ConstraintException {
        int convertedWeight;
        String[] splitWeight = weight.split("\\.");
        if (splitWeight.length > 2) {
            throw new DeserializeException("Given weight ( " + weight + " ) format is not supported");
        }

        switch (splitWeight.length) {
            case 1:
                weight = padPrecision(weight, MAX_PRECISION);
                break;
            case 2:
                if (splitWeight[1].length() > MAX_PRECISION) {
                    throw new DeserializeException("Given weight ( " + weight + " ) exceeds the supported precision");
                }
                weight = padPrecision(weight, MAX_PRECISION - splitWeight[1].length());
                break;
            default:
                throw new DeserializeException("Given weight ( " + weight + " ) is malformed");
        }

        convertedWeight = Integer.parseInt(weight.replace(".", ""));
        weightValidator.validate(convertedWeight);
        return convertedWeight;
    }

    private String padPrecision(String weight, int precision) {
        return StringUtils.rightPad(weight, weight.length() + precision, "0");
    }
}
