package fr.unk.contrainte;

import fr.unk.domaine.DomainMap;
import fr.unk.variable.Getter;
import fr.unk.variable.Variable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is an abstract for all the Constraint of the CSP
 * @param <T> The type of the value
 */
public abstract class Constraint<T> {

    List<Variable<T>> leftVar, rightVar;

    protected static <T> List<Variable<T>> toVariableList(Collection<Getter<T>> getterList){

        List<Variable<T>> variableList = new ArrayList<>();

        if(getterList == null)
            return variableList;

        for(Getter<T> getter : getterList){
            if (!getter.isVar())
                continue;
            variableList.addAll(((Variable<T>) getter).getVariableList());
        }

        return variableList;

    }

    protected static <T> List<Variable<T>> withoutValue(Collection<Variable<T>> withoutValue){

        List<Variable<T>> variableList = new ArrayList<>();

        for (Variable<T> variable : withoutValue)
            if (!variable.isCalcul() && variable.getValue() == null)
                variableList.add(variable);

        return variableList;

    }

    protected Constraint(List<Getter<T>> leftVar, List<Getter<T>> rightVar){
        this.leftVar = toVariableList(leftVar);
        this.rightVar = toVariableList(rightVar);
    }

    protected Constraint() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    protected Constraint(Getter<T> leftVar, Getter<T> rightVar){
        this(new ArrayList<>() {{
            if(leftVar != null)
                add(leftVar);
        }}, new ArrayList<>() {{
            if(rightVar != null)
                add(rightVar);
        }});
    }

    /**
     * Check if a constraint is satisfied or not
     * @return true if the constraint is satisfied or null if it is not possible to check
     */
    public abstract Boolean trySatisfied();

    /**
     * Check if a constraint is satisfied or not
     * @return true if the constraint is satisfied
     */
    public boolean satisfied(){
        Boolean s = trySatisfied();
        if(s == null)
            return false;
        return s;
    }

    protected List<Variable<T>> getVarOnLeft(){
        return leftVar;
    }
    protected List<Variable<T>> getVarOnRight(){
        return rightVar;
    }

    /**
     * Reduce the domain of var in function of the Constraint
     * @param domainMap All the domain to reduce if needed
     * @return false if this reduction remove all var possibility
     */
    public abstract boolean reduceDomain(DomainMap<T> domainMap);

    /**
     * Register all of the constraint to the var is checking
     */
    public void registerToVar(){
        this.leftVar.forEach(variable -> variable.getConstrainst().add(this));
        this.rightVar.forEach(variable -> variable.getConstrainst().add(this));
    }

}
