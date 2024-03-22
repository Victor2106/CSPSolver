package fr.unk.domaine.number;

import fr.unk.domaine.Domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain of integer
 */
public class IntDomain implements Domain<Integer> {

    final List<Integer> intList;


    /**
     * Constructor with a list of integers that represent the domain
     * @param integers double list
     */
    public IntDomain(List<Integer> integers){
        this.intList = integers;
    }

    /**
     * This will generate a domain with the between min and max included with a jump between each value
     * @param min the start value of the domain
     * @param max the maximum value that are in the domain
     * @param jump the jump between each value generated
     */
    public IntDomain(Integer min, Integer max, Integer jump){
        Integer current = min;
        this.intList = new ArrayList<>();
        if (jump <= 0)
            return;
        while (current <= max){
            this.intList.add(current);
            current += jump;
        }
    }

    @Override
    public List<Integer> getPossibility() {
        return this.intList;
    }

    @Override
    public Domain<Integer> duplicate() {
        List<Integer> intList = new ArrayList<>(getPossibility());
        return new IntDomain(intList);
    }
}
