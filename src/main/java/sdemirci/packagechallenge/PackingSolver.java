package sdemirci.packagechallenge;

import sdemirci.packagechallenge.data.Case;
import sdemirci.packagechallenge.data.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.StrictMath.min;

public class PackingSolver {
    public List<Integer> solve(Case caseItem) {
        if (caseItem.getMaxTotalWeight() < 0) {
            throw new IllegalArgumentException("Total max weight of an package cannot has a negative value");
        }
        LookupTableItem[][] lookupTable = buildTable(caseItem);
        return findSolutionSet(lookupTable, caseItem.getItems());
    }

    private LookupTableItem[][] buildTable(Case caseItem) {
        int rowCount = caseItem.getItems().size() + 1;
        int columnCount = caseItem.getMaxTotalWeight() + 1;
        LookupTableItem[][] lookupTable = new LookupTableItem[rowCount][columnCount];

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                if (rowIndex == 0 || columnIndex == 0) {
                    lookupTable[rowIndex][columnIndex] = LookupTableItem.ZERO_ITEM;
                } else {
                    Item candidate = caseItem.getItems().get(rowIndex - 1);
                    if (candidate.getWeight() <= columnIndex) {
                        // Price having candidate plus best price with remaining weight
                        int priceWithCandidate = candidate.getPrice() + lookupTable[rowIndex - 1][columnIndex - candidate.getWeight()].getPrice();
                        // Price with not having candidate is best price for one less items with current weight
                        int priceWithoutCandidate = lookupTable[rowIndex - 1][columnIndex].getPrice();

                        // Weight values are stored to pick the lighter item set in case of different item sets have the same price
                        int weightWithCandidate = candidate.getWeight() + lookupTable[rowIndex - 1][columnIndex - candidate.getWeight()].getWeight();
                        int weightWithoutCandidate = lookupTable[rowIndex - 1][columnIndex].getWeight();

                        // Select candidate if selecting increases price, or, in equality, selecting decreases weight.
                        if (priceWithCandidate == priceWithoutCandidate) {
                            // Min weight is stored to pick lower total weight while selecting package items
                            lookupTable[rowIndex][columnIndex] = new LookupTableItem(priceWithCandidate, min(weightWithCandidate, weightWithoutCandidate));
                        } else if (priceWithCandidate > priceWithoutCandidate) {
                            lookupTable[rowIndex][columnIndex] = new LookupTableItem(priceWithCandidate, weightWithCandidate);
                        } else {
                            lookupTable[rowIndex][columnIndex] = new LookupTableItem(priceWithoutCandidate, weightWithoutCandidate);
                        }
                    } else {
                        lookupTable[rowIndex][columnIndex] = lookupTable[rowIndex - 1][columnIndex];
                    }
                }
            }
        }

        return lookupTable;
    }

    private List<Integer> findSolutionSet(LookupTableItem[][] lookupTable, List<Item> items) {
        List<Integer> solutionSet = new ArrayList<>();

        int rowCount = lookupTable.length;
        int columnCount = lookupTable[0].length;

        int remainingWeight = columnCount - 1; //Initialize to total weight
        for (int row = rowCount - 1; row > 0; row--) {
            if (lookupTable[row][remainingWeight].getPrice() != 0) {
                // Current item is added to solution if either it has better price or same price with less weight
                // Same price with same weight item with smaller index is selected (in next iteration)
                if (lookupTable[row][remainingWeight].getPrice() != lookupTable[row - 1][remainingWeight].getPrice()
                        || lookupTable[row][remainingWeight].getWeight() < lookupTable[row - 1][remainingWeight].getWeight()) {
                    solutionSet.add(row);
                    remainingWeight = remainingWeight - items.get(row - 1).getWeight();
                }
            }
        }

        Collections.reverse(solutionSet);
        return solutionSet;
    }

    private static class LookupTableItem {
        private final int price;
        private final int weight;

        private static final LookupTableItem ZERO_ITEM = new LookupTableItem(0, 0);

        private LookupTableItem(int price, int weight) {
            this.price = price;
            this.weight = weight;
        }

        public int getPrice() {
            return price;
        }

        public int getWeight() {
            return weight;
        }
    }
}
