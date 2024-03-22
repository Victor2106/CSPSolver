package fr.unk;

import fr.unk.contrainte.Constraint;
import fr.unk.domaine.Domain;
import fr.unk.domaine.DomainMap;
import fr.unk.variable.Variable;

import java.util.*;

/**
 * Main class for the solver where you will register constraint, var and their domain and start the solve processing
 * @param <T> the type of the value
 */
public class CSPSolver<T> {

    List<Constraint<T>> constraintList = new ArrayList<>();
    private final DomainMap<T> solverDomain = new DomainMap<>();

    /**
     * Default constructor to initialise the class
     */
    public CSPSolver(){

    }

    /**
     * Register var and its domain to iterate on when trySolve is called
     * @param variable the var to iterate on
     * @param domain the domain of the var
     */
    public void addUnknownVariable(Variable<T> variable, Domain<T> domain){
        if(hasUnknownVariable(variable))
            return;
        this.solverDomain.addDomain(variable, domain);
    }

    /**
     * Get the domain map
     * @return the current map domain
     */
    public DomainMap<T> getDomainMap(){
        return this.solverDomain;
    }

    /**
     * Add constraint to solver
     * @param constraint Constraint to check on while try to solve
     */
    public void addConstraint(Constraint<T> constraint){
        this.constraintList.add(constraint);
    }

    /**
     * Check if a var is registered
     * @param variable the var
     * @return true if variable is stored
     */
    public boolean hasUnknownVariable(Variable<T> variable) {
        return this.solverDomain.getMap().containsKey(variable);
    }

    /**
     * Get registered domain for var
     * @param variable the var of the domain we want
     * @return the domain of the var
     */
    public Domain<T> getDefinedDomain(Variable<T> variable) {
        return this.solverDomain.getMap().get(variable);
    }

    /**
     * Attribute value to var and reduce its domain
     * @param domain domain to reduce
     * @param variable variable which value gonna be attribuated
     * @param value the new var value
     * @return True if there is at least one solution
     */
    public boolean setAndReduce(DomainMap<T> domain, Variable<T> variable, T value){
        print("Set: "+variable.getVarName()+" value: "+value, 0);
        variable.setValue(value);
        List<T> po = domain.getDomain(variable).getPossibility();
        po.clear();
        po.add(value);
        for(Constraint<T> constraint : variable.getConstrainst()) {
            if (!constraint.reduceDomain(domain)) {
                variable.setValue(null);
                return false;
            }
        }
        return true;
    }

    /**
     * Function will search for the domain with low amount of possibility and start
     * the search of a valid value on it
     * @param domainMap current domain map to search on
     * @return True if a solution is found
     */
    public boolean solveDomain(DomainMap<T> domainMap){

        Variable<T> next = null;
        int nextSize = Integer.MAX_VALUE;
        int calcul = 0;

        for (Map.Entry<Variable<T>, Domain<T>> entry : domainMap.getMap().entrySet()) {
            Variable<T> checkVar = entry.getKey();
            Domain<T> checkDomain = entry.getValue();
            int size = checkDomain.getPossibility().size();
            if(size == 1) {
                T onlyVal = checkDomain.getPossibility().getFirst();
                if(onlyVal != null && checkVar.getValue() != onlyVal) {
                    if (!setAndReduce(domainMap, checkVar, onlyVal)) {
                        return false;
                    }
                }
                calcul++;
                continue;
            }else if(size>=1){
                if(checkVar.getValue() != null) {
                    checkVar.setValue(null);
                }
            }
            if(next == null || size<nextSize){
                nextSize = size;
                next = checkVar;
            }
        }

        if (calcul == domainMap.getMap().size()) {
            return true;
        }

        if(next == null) {
            return false;
        }

        return solve(next, domainMap);
    }

    /**
     * Iterate on all domain possibility for the var and call solveDomain for the other domain
     * @param variable variable to test all possibility
     * @param domain All of the domain
     * @return True if solution is found
     */
    private boolean solve(Variable<T> variable, DomainMap<T> domain) {

        Domain<T> tDomain = domain.getDomain(variable);

        for (T t : tDomain.getPossibility()){

            if(t == null)
                continue;

            DomainMap<T> newDomain = domain.duplicate();

            boolean res = setAndReduce(newDomain, variable, t);

            if(!res)
                continue;

            if(solveDomain(newDomain))
                return true;

        }

        return false;
    }

    /**
     * Start all of the solving process, it start the iteration of every value available for each var
     * @return Map with VarName as Key and the variable value
     */
    public Map<String, T> trySolve() {

        this.solverDomain.getMap().forEach((variable, domain) -> variable.getConstrainst().clear());

        this.constraintList.forEach(Constraint::registerToVar);

        DomainMap<T> newDomain = this.solverDomain.duplicate();

        for(Map.Entry<Variable<T>, Domain<T>> entry : newDomain.getMap().entrySet()){

            Collections.shuffle(entry.getValue().getPossibility());

            T curVal = entry.getKey().getValue();
            if(curVal == null)
                continue;

            if (!this.setAndReduce(newDomain, entry.getKey(), curVal)) {

                return null;
            }
        }

        for(Constraint<T> constraint : this.constraintList){
            Boolean ts = constraint.trySatisfied();
            if(ts == null)
                constraint.reduceDomain(newDomain);
            if(Boolean.FALSE.equals(ts)) {

                return null;
            }
        }

        if (!this.solveDomain(newDomain)) {
            return null;
        }

        return new HashMap<>() {{
            for (Variable<T> variable : newDomain.getMap().keySet()) {
                if (variable.getValue() != null)
                    put(variable.getVarName(), variable.getValue());
            }
        }};

    }

    private void print(String name, int i){
        /*
        for(int i2 = 0; i2<i; i2++){
            System.out.print("-");
        }
        System.out.println(name);
        */
    }

}
