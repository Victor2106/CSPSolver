package fr.unk.variable.numvar;

import fr.unk.variable.Getter;

/**
 * This class is extending the Calcul class with a float value type
 */
public class CSPFloat extends Calcul<Float> {

    /**
     * Constructor with the varName for the Variable constructor
     * @param varName the name of the var
     */
    public CSPFloat(String varName) {
        super(varName);
    }

    CSPFloat(String varName, Operation<Float> operationList){
        super(varName, operationList);
    }

    @Override
    public CSPFloat add(Getter<Float> variable){
        return this.newCopy(new Operation<>(Float::sum, (int1, int2) -> int1-int2, this, variable));
    }

    @Override
    public CSPFloat remove(Getter<Float> variable){
        return this.newCopy(new Operation<>((int1, int2) -> int1-int2, Float::sum, this, variable));
    }

    @Override
    public CSPFloat divide(Getter<Float> variable) {
        return this.newCopy(new Operation<>((int1, int2) -> int1/int2, (int1, int2) -> int1*int2, this, variable));
    }

    @Override
    public CSPFloat multiply(Getter<Float> variable) {
        return this.newCopy(new Operation<>((int1, int2) -> int1*int2, (int1, int2) -> int1/int2, this, variable));
    }

    @Override
    public CSPFloat modulo(Getter<Float> variable) {
        return this.newCopy(new Operation<>((int1, int2) -> int1 % int2, this, variable));
    }

    @Override
    CSPFloat newCopy(Operation<Float> operations) {
        CSPFloat cspDouble = new CSPFloat("", operations);
        operations.addDepend(cspDouble);
        return cspDouble;
    }
}
