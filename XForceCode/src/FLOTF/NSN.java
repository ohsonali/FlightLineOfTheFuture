//File: NSN.java
//NSN object class with get and set methods

package FLOTF;

import javafx.beans.property.SimpleStringProperty;

/**
 * This class creates an NSN object type that holds stock number, description, and cage
 * @author Bernard Chan, Sonali Loomba
 */

public class NSN {
    /** Stores stock number */
    private SimpleStringProperty nsn;
    /** Stores description of a particular NSN */
    private SimpleStringProperty description;
    /** Stores cages of a particular NSN */
    private SimpleStringProperty cage;

    /**
     * NSN constructor
     * @param nsn the stock number
     * @param description description of particular NSN object
     * @param cage cage(s) of particular NSN object
     */
    public NSN (String nsn, String description, String cage){
        this.nsn = new SimpleStringProperty(nsn);
        this.description = new SimpleStringProperty(description);
        this.cage = new SimpleStringProperty(cage);
    }

    /**
     * Gets the National Stock Number
     * @return a String specifying the stock number
     */
    public String getNsn() {
        return nsn.get();
    }

    /**
     * Sets the National Stock Number
     * @param nsn the NSN
     */
    public void setNSN(String nsn) {
        this.nsn.set(nsn);
    }
    /**
     * Gets the description of particular NSN object
     * @return a String specifying the description
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Sets the NSN description
     * @param description the NSN description
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * Gets the cage(s) of particular NSN object
     * @return a String specifying the cages
     */
    public String getCage() {
        return cage.get();
    }

    /**
     * Sets the NSN cage(s)
     * @param cage the cage number(s)
     */
    public void setCage(String cage) {
        this.cage.set(cage);
    }

    /**
     * Prints specified object's stock number
     * @return a String specifying the stock number
     */
    @Override
    public String toString(){
        return nsn.get();
    }
}

