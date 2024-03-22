package fr.unk.domaine;

import java.util.List;

/**
 * Store all possible value for a var
 * @param <T> the value type
 */
public interface Domain<T> {

    /**
     * Get all of the domain possibility
     * @return A list of all the possibility
     */
    List<T> getPossibility();

    /**
     * Duplicate the domain
     * @return A duplicate of the domain
     */
    Domain<T> duplicate();
}
