package fr.unk.util;

/**
 * Permit to stock two value of different type
 * @param <L> first value type
 * @param <R> second value type
 */
public class Pair <L,R>{

    final L l;
    final R r;

    /**
     * Store as a pair the left and right value
     * @param l the left value (key)
     * @param r the right value (value)
     */
    public Pair(L l, R r){
        this.l = l;
        this.r = r;
    }

    /**
     * Get the right value (value)
     * @return the right value (value)
     */
    public R getR() {
        return r;
    }

    /**
     * Get the left value (value)
     * @return the left value (value)
     */
    public L getL() {
        return l;
    }

    @Override
    public String toString(){
        return "L: "+l+" R: "+r;
    }

}
