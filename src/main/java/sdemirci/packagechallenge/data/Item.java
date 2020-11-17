package sdemirci.packagechallenge.data;

import java.util.Objects;

public class Item {
    private final int index;
    private final int weight;
    private final int price;

    public Item(int index, int weight, int price) {
        this.index = index;
        this.weight = weight;
        this.price = price;
    }

    public int getIndex() {
        return index;
    }

    public int getWeight() {
        return weight;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return index == item.index &&
                weight == item.weight &&
                price == item.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, weight, price);
    }
}
