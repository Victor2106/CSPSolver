package fr.unk.contrainte.vc;

import fr.unk.contrainte.Constraint;
import fr.unk.domaine.DomainMap;
import fr.unk.util.Pair;
import fr.unk.variable.Getter;
import fr.unk.variable.Variable;
import fr.unk.variable.numvar.Calcul;

import java.util.List;

/**
 * Check if two value are different
 * @param <T> the Type of the value which need to be comparable
 */
public class Diff<T extends Comparable<T>> extends Constraint<T> {

    final Getter<T> fv;
    final Getter<T> sv;

    /**
     * Constructor with the value on the left and the right of the comparison, fv different of sv
     * @param fv the left value
     * @param sv the right value
     */
    public Diff(Getter<T> fv, Getter<T> sv){
        super(fv, sv);
        this.fv = fv;
        this.sv = sv;
    }

    /**
     * Constructor with the value on the left and the right of the comparison, fv different of sv
     * @param fv the left value
     * @param sv the right value
     */
    public Diff(T fv, Getter<T> sv){
        this(new Getter<>(fv), sv);
    }

    /**
     * Constructor with the value on the left and the right of the comparison, fv different of sv
     * @param fv the left value
     * @param sv the right value
     */
    public Diff(Getter<T> fv, T sv){
        this(fv, new Getter<>(sv));
    }

    @Override
    public Boolean trySatisfied() {
        T f1 = fv.getValue();
        T f2 = sv.getValue();
        if(f1 == null || f2 == null)
            return null;
        return f1.compareTo(f2) != 0;
    }

    private boolean red(DomainMap<T> domainMap, Getter<T> alreadySet, Variable<T> notSet){

        if(notSet.getValue() != null || alreadySet.getValue() == null)
            return true;

        List<T> domainList;
        T toRemove = alreadySet.getValue();

        if(notSet.isCalcul()){

            Pair<Variable<T>, T> pair = ((Calcul<T>) notSet).getRevert(alreadySet.getValue());
            if(pair == null)
                return true;
            domainList = domainMap.getDomain(pair.getL()).getPossibility();
            toRemove = pair.getR();

        }else {
            domainList = domainMap.getDomain(notSet).getPossibility();
            notSet.invalidate();
        }

        domainList.remove(toRemove);
        return !domainList.isEmpty();

    }

    @Override
    public boolean reduceDomain(DomainMap<T> domainMap){

        if(sv instanceof Variable<T>)
            if(!this.red(domainMap, fv, (Variable<T>) sv))
                return false;

        if(fv instanceof Variable<T>)
            return this.red(domainMap, sv, (Variable<T>) fv);

        return true;

    }

}