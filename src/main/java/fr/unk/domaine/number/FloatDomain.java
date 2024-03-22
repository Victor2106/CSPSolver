package fr.unk.domaine.number;

import fr.unk.domaine.Domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain of Float
 */
public class FloatDomain implements Domain<Float> {

    final List<Float> floatList;

    /**
     * Constructor with a list of float that represent the domain
     * @param floats double list
     */
    public FloatDomain(List<Float> floats){
        this.floatList = floats;
    }

    /**
     * This will generate a domain with the between min and max included with a jump between each value
     * @param min the start value of the domain
     * @param max the maximum value that are in the domain
     * @param jump the jump between each value generated
     */
    public FloatDomain(float min, float max, float jump){
        float current = min;
        this.floatList = new ArrayList<>();
        if (jump <= 0)
            return;
        while (current <= max){
            this.floatList.add(current);
            current += jump;
        }
    }

    @Override
    public List<Float> getPossibility() {
        return this.floatList;
    }

    @Override
    public Domain<Float> duplicate() {
        return new FloatDomain(new ArrayList<>(this.floatList));
    }
}
