package fr.unk.variable.numvar;

import fr.unk.variable.Getter;

/**
 * This class is extending the Calcul class with a double value type
 */
public class CSPDouble extends Calcul<Double> {

    /**
     * Constructor with the varName for the Variable constructor
     * @param varName the name of the var
     */
    public CSPDouble(String varName) {
        super(varName);
    }

    CSPDouble(String varName, Operation<Double> operationList) {
        super(varName, operationList);
    }

    @Override
    public CSPDouble add(Getter<Double> variable){
        return this.newCopy(new Operation<>(Double::sum, (int1, int2) -> int1-int2, this, variable));
    }

    @Override
    public CSPDouble remove(Getter<Double> variable){
        return this.newCopy(new Operation<>((int1, int2) -> int1-int2, Double::sum, this, variable));
    }

    @Override
    public CSPDouble divide(Getter<Double> variable) {
        return this.newCopy(new Operation<>((int1, int2) -> int1/int2, (int1, int2) -> int1*int2, this, variable));
    }

    @Override
    public CSPDouble multiply(Getter<Double> variable) {
        return this.newCopy(new Operation<>((int1, int2) -> int1*int2, (int1, int2) -> int1/int2, this, variable));
    }

    @Override
    public CSPDouble modulo(Getter<Double> variable) {
        return this.newCopy(new Operation<>((int1, int2) -> int1 % int2, this, variable));
    }

    @Override
    CSPDouble newCopy(Operation<Double> operations) {
        CSPDouble cspDouble = new CSPDouble("", operations);
        operations.addDepend(cspDouble);
        return cspDouble;
    }
}
