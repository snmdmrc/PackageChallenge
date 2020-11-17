package sdemirci.packagechallenge.data;

import java.util.List;
import java.util.Objects;

public class Case {
    private final int maxTotalWeight;
    private final List<Item> items;

    public Case(int maxTotalWeight, List<Item> items) {
        this.maxTotalWeight = maxTotalWeight;
        this.items = items;
    }

    public int getMaxTotalWeight() {
        return maxTotalWeight;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Case aCase = (Case) o;
        return maxTotalWeight == aCase.maxTotalWeight &&
                items.equals(aCase.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxTotalWeight, items);
    }
}
