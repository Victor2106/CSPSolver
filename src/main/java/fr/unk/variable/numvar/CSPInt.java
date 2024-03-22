package fr.unk.variable.numvar;

import fr.unk.variable.Getter;

/**
 * This class is extending the Calcul class with a int value type
 */
public class CSPInt extends Calcul<Integer> {

    /**
     * Constructor with the varName for the Variable constructor
     * @param varName the name of the var
     */
    public CSPInt(String varName) {
        super(varName);
    }

    CSPInt(String varName, Operation<Integer> operationList){
        super(varName, operationList);
    }

    @Override
    public CSPInt add(Getter<Integer> variable){
        return this.newCopy(new Operation<>(Integer::sum, (int1, int2) -> int1-int2, this, variable));
    }

    @Override
    public CSPInt remove(Getter<Integer> variable){
        return this.newCopy(new Operation<>((int1, int2) -> int1-int2, Integer::sum, this, variable));
    }

    @Override
    public CSPInt divide(Getter<Integer> variable) {
        return this.newCopy(new Operation<>((int1, int2) -> int1/int2, (int1, int2) -> int1*int2, this, variable));
    }

    @Override
    public CSPInt multiply(Getter<Integer> variable) {
        return this.newCopy(new Operation<>((int1, int2) -> int1*int2, (int1, int2) -> int1/int2, this, variable));
    }

    @Override
    public CSPInt modulo(Getter<Integer> variable) {
        return this.newCopy(new Operation<>((int1, int2) -> int1 % int2, this, variable));
    }

    @Override
    CSPInt newCopy(Operation<Integer> operations) {
        CSPInt cspDouble = new CSPInt("", operations);
        operations.addDepend(cspDouble);
        return cspDouble;
    }
}
