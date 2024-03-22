package fr.unk.variable.numvar;

import fr.unk.variable.Getter;
import fr.unk.variable.Variable;

import java.util.function.BinaryOperator;

/**
 * Store the operation and the variable for the Calcul class
 * @param <T> the value type
 */
public class Operation<T> {

    private final BinaryOperator<T> binaryOperator, revertOperator;
    private final Getter<T> variable, previous;

    protected Operation(BinaryOperator<T> binaryOperator, BinaryOperator<T> revertOperator, Getter<T> previous, Getter<T> variable){
        this.binaryOperator = binaryOperator;
        this.revertOperator = revertOperator;
        this.variable = variable;
        this.previous = previous;
    }

    protected Operation(BinaryOperator<T> binaryOperator, Getter<T> previous, Getter<T> variable){
        this(binaryOperator, null, previous, variable);
    }

    /**
     * The current calcul
     * @return the binaryoperator class who will made the operation
     */
    public BinaryOperator<T> getBinaryOperator() {
        return binaryOperator;
    }

    /**
     * Contains the operation to reverse the result
     * @return the binaryoperator class who will revert the operation
     */
    public BinaryOperator<T> getRevertOperator() {
        return revertOperator;
    }

    /**
     * Get the first value of the calcul
     * @return the Getter who permit to get the first value of the operation
     */
    public Getter<T> getPrevious() {
        return previous;
    }

    /**
     * Get the second value of the calcul
     * @return the Getter who permit to get the second value of the operation
     */
    public Getter<T> getVariable() {
        return variable;
    }

    /**
     * Register that dependVar depend on the value of the two other variable
     * @param dependVar the variable that depend on the two other
     */
    protected void addDepend(Variable<T> dependVar){
        if(variable.isVar())
            ((Variable<T>) variable).addDepend(dependVar);
        if (previous != null && previous.isVar())
            ((Variable<T>) previous).addDepend(dependVar);
    }

}
