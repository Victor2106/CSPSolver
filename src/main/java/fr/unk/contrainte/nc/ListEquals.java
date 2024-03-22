package fr.unk.contrainte.nc;

import fr.unk.contrainte.Constraint;
import fr.unk.domaine.Domain;
import fr.unk.domaine.DomainMap;
import fr.unk.util.Pair;
import fr.unk.variable.Getter;
import fr.unk.variable.Variable;
import fr.unk.variable.numvar.Calcul;

import java.util.List;

/**
 * Check if a list of value are equals between each other
 * @param <T> the Type of the value which need to be comparable
 */
public class ListEquals<T extends Comparable<T>> extends Constraint<T> {

    final List<Getter<T>> variableList;

    /**
     * Constructor with the list of value that need to be equals to each other
     * @param variables list of variables
     */
    public ListEquals(List<Getter<T>> variables){
        super(variables, null);
        this.variableList = variables;
    }

    @Override
    public Boolean trySatisfied() {

        if (variableList.isEmpty())
            return false;

        for (Getter<T> fGetter : this.variableList) {
            if (fGetter.getValue() == null) {
                return null;
            }
        }

        T fv = variableList.getFirst().getValue();
        if (fv == null)
            return null;

        for (Getter<T> variable : variableList) {
            T vi = variable.getValue();
            if(vi == null)
                return null;
            if (fv.compareTo(vi) != 0)
                return false;
        }

        return true;
    }

    @Override
    public boolean reduceDomain(DomainMap<T> domainMap) {

        for (Getter<T> fGetter : this.variableList) {

            T fVal = fGetter.getValue();

            if (fVal == null)
                continue;

            for (Getter<T> sGetter : this.variableList) {

                if (sGetter == fGetter || !sGetter.isVar()) {
                    continue;
                }

                Domain<T> removeDomain;
                T removeVal;

                if (sGetter.isCalcul()) {
                    Pair<Variable<T>, T> revertValue = ((Calcul<T>) sGetter).getRevert(fVal);
                    if(revertValue == null)
                        continue;
                    removeDomain = domainMap.getDomain(revertValue.getL());
                    removeVal = revertValue.getR();
                } else {
                    removeDomain = domainMap.getDomain((Variable<T>) sGetter);
                    removeVal = fVal;
                }

                if (removeDomain == null) {
                    continue;
                }

                boolean as = removeDomain.getPossibility().contains(removeVal);

                removeDomain.getPossibility().clear();
                if (as)
                    removeDomain.getPossibility().add(removeVal);
                else
                    return false;

            }

        }

        return true;

    }

}
