package fr.unk.contrainte.vc;

import fr.unk.contrainte.Constraint;
import fr.unk.domaine.Domain;
import fr.unk.domaine.DomainMap;
import fr.unk.variable.Getter;
import fr.unk.variable.Variable;

import java.util.List;

/**
 * Check if a value is inferior or equals to another
 * @param <T> the Type of the value which need to be comparable
 */
public class Sup<T extends Comparable<T>> extends Constraint<T> {

    final Getter<T> fv;
    final Getter<T> sv;

    final boolean equals;

    /**
     * Constructor with the value on the left and the right of the comparison, fv superior to sv
     * @param fv the left value
     * @param sv the right value
     * @param equals if it is inferior / inferior equals to
     */
    public Sup(Getter<T> fv, Getter<T> sv, boolean equals){
        super(fv, sv);
        this.fv = fv;
        this.sv = sv;
        this.equals = equals;
    }

    /**
     * Constructor with the value on the left and the right of the comparison, fv superior to sv
     * @param fv the left value
     * @param sv the right value
     * @param equals if it is inferior / inferior equals to
     */
    public Sup(T fv, Getter<T> sv, boolean equals){
        this(new Getter<>(fv), sv, equals);
    }

    /**
     * Constructor with the value on the left and the right of the comparison, fv superior to sv
     * @param fv the left value
     * @param sv the right value
     * @param equals if it is inferior / inferior equals to
     */
    public Sup(Getter<T> fv, T sv, boolean equals){
        this(fv, new Getter<>(sv), equals);
    }

    @Override
    public Boolean trySatisfied() {
        T f1 = fv.getValue();
        T f2 = sv.getValue();
        if(f1 == null || f2 == null)
            return null;
        int val = f1.compareTo(f2);
        return equals ? val >= 0 : val > 0;
    }

    @Override
    public boolean reduceDomain(DomainMap<T> domainMap){

        T fValue = fv.getValue(), sValue = sv.getValue();
        if(fValue == null && sValue == null)
            return true;

        if(fValue != null && sValue != null)
            return true;

        Variable<T> toIterate;

        if(fValue != null){

            if(!(sv instanceof Variable<T>))
                return false;

            List<Variable<T>> toIterateList = Constraint.withoutValue(this.getVarOnRight());

            if(toIterateList.size() != 1)
                return true;

            toIterate = toIterateList.getFirst();

        }else{

            if(!(fv instanceof Variable<T>))
                return false;

            List<Variable<T>> toIterateList = Constraint.withoutValue(this.getVarOnLeft());
            if(toIterateList.size() != 1)
                return true;

            toIterate = toIterateList.getFirst();

        }

        Domain<T> ogDomain = domainMap.getDomain(toIterate);

        for (T iter : ogDomain.duplicate().getPossibility()){

            toIterate.setValue(iter);
            if(!satisfied())
                ogDomain.getPossibility().remove(iter);

        }

        toIterate.setValue(null);

        return !ogDomain.getPossibility().isEmpty();

    }

}
