package sdemirci.packagechallenge.data;

import sdemirci.packagechallenge.exceptions.ConstraintException;
import sdemirci.packagechallenge.exceptions.DeserializeException;

import static sdemirci.packagechallenge.data.Constants.CURRENCY_SIGN;

public class ItemDeserializer {
    private final ItemValidator itemValidator;
    private final WeightConverter weightConverter;

    public ItemDeserializer(ItemValidator itemValidator, WeightConverter weightConverter) {
        this.itemValidator = itemValidator;
        this.weightConverter = weightConverter;
    }

    public Item deserialize(String item) throws DeserializeException {
        String[] splitItem = item.split(",");
        if (splitItem.length != 3) {
            throw new DeserializeException("Given item ( " + item + " ) is not as supported");
        }

        try {
            Item createdItem = new Item(Integer.parseInt(splitItem[0]), weightConverter.convert(splitItem[1].trim()), extractPrice(splitItem[2]));
            itemValidator.validate(createdItem);

            return createdItem;
        } catch (NumberFormatException | ConstraintException exception) {
            throw new DeserializeException("Failed to deserialize item " + item + " with error " + exception.getMessage(), exception);
        }
    }

    private int extractPrice(String price) throws DeserializeException {
        String[] splitPrice = price.split(CURRENCY_SIGN);
        if (splitPrice.length != 2) {
            throw new DeserializeException("Given price value ( " + price + " ) is not as supported");
        }
        return Integer.parseInt(splitPrice[1]);
    }
}
