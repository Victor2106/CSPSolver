package fr.unk.domaine;

import fr.unk.variable.Variable;

import java.util.HashMap;
import java.util.Map;

/**
 * Permit to map Var with its domain and duplicate the for the application
 * @param <T> The type of the value
 */
public class DomainMap<T> {

    private final Map<Variable<T>, Domain<T>> uknVariables = new HashMap<>();

    /**
     * Permit to initialise the class
     */
    public DomainMap(){

    }

    /**
     * Get the full map with its variable and the domain linked
     * @return the current map
     */
    public Map<Variable<T>, Domain<T>> getMap() {
        return uknVariables;
    }

    /**
     * Add a variable and its domain to the Map
     * @param variable variable to register as key
     * @param domain variable domain
     */
    public void addDomain(Variable<T> variable, Domain<T> domain){
        this.uknVariables.put(variable, domain);
    }

    /**
     * Get the stored domain of a var
     * @param variable The variable to get the linked domain
     * @return The domain of the var
     */
    public Domain<T> getDomain(Variable<T> variable){
        return this.uknVariables.get(variable);
    }

    /**
     * Duplicate everything to a new instance of DomainMap
     * @return The duplicate of the current instance
     */
    public DomainMap<T> duplicate(){
        DomainMap<T> domainMap = new DomainMap<>();
        for (Map.Entry<Variable<T>, Domain<T>> entry : this.getMap().entrySet()){
            domainMap.addDomain(entry.getKey(), entry.getValue().duplicate());
        }
        return domainMap;
    }

}
