package fr.unk.domaine.number;

import fr.unk.domaine.Domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain of double
 */
public class DoubleDomain implements Domain<Double> {

    final List<Double> doubleList;

    /**
     * Constructor with a list of double that represent the domain
     * @param doubles double list
     */
    public DoubleDomain(List<Double> doubles){
        this.doubleList = doubles;
    }

    /**
     * This will generate a domain with the between min and max included with a jump between each value
     * @param min the start value of the domain
     * @param max the maximum value that are in the domain
     * @param jump the jump between each value generated
     */
    public DoubleDomain(double min, double max, double jump){
        double current = min;
        this.doubleList = new ArrayList<>();
        if (jump <= 0)
            return;
        while (current <= max){
            this.doubleList.add(current);
            current += jump;
        }
    }

    @Override
    public List<Double> getPossibility() {
        return this.doubleList;
    }

    @Override
    public Domain<Double> duplicate() {
        return new DoubleDomain(new ArrayList<>(this.doubleList));
    }
}
