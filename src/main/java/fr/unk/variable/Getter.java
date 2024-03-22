package fr.unk.variable;

/**
 * This class is directly use if the value is static
 * @param <T> The value type (int, double)
 */
public class Getter<T> {

    T t;

    /**
     * Constructor to generate the Getter with a static value
     * @throws NullPointerException if variable is a Getter himself
     * @param variable the value returned by the getter
     */
    public Getter(T variable){
        if(variable instanceof Getter<?>)
            throw new NullPointerException("VarGetter cannot be VarGetter of himself ");
        this.t = variable;
    }

    /**
     * Get if it's a var
     * @return true if var
     */
    public boolean isVar(){
        return false;
    }

    /**
     * Get if it's a var with calcul
     * @return true if it has calcul
     */
    public boolean isCalcul(){
        return false;
    }

    /**
     * Get the value of the Getter
     * @return null or the value
     */
    public T getValue(){
        return this.t;
    }

}
