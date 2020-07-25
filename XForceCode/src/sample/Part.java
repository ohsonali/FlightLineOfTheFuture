//File: Part.java
//Part object class with get and set methods

package sample;

import javafx.beans.property.SimpleStringProperty;

/**
 * This class creates an NSN object type that holds index, part number, cage number, description, units of assembly,
 * usable on code, and SMR code
 * @author Bernard Chan, Sonali Loomba
 */

public class Part {
    /** Stores index of particular part */
    private SimpleStringProperty index;
    /** Stores part number of particular part */
    private SimpleStringProperty partNum;
    /** Stores cage number of particular part */
    private SimpleStringProperty cage;
    /** Stores description of particular part */
    private SimpleStringProperty description;
    /** Stores units of assembly of particular part */
    private SimpleStringProperty units;
    /** Stores usable on code of particular part */
    private SimpleStringProperty usable;
    /** Stores SMR code of particular part */
    private SimpleStringProperty smr;


    /**
     * Part constructor
     * @param index index of particular part
     * @param partNum part number of particular part
     * @param cage cage number of particular part
     * @param description description of particular part
     * @param units units of assembly of particular part
     * @param usable usable on code of particular part
     * @param smr SMR code of particular part
     */
    public Part(String index, String partNum, String cage, String description, String units, String usable, String smr){
        this.index = new SimpleStringProperty(index);
        this.partNum = new SimpleStringProperty(partNum);
        this.cage = new SimpleStringProperty(cage);
        this.description = new SimpleStringProperty( description);
        this.units = new SimpleStringProperty(units);
        this.usable = new SimpleStringProperty(usable);
        this.smr = new SimpleStringProperty(smr);
    }

    /**
     * Gets the index of particular Part object
     * @return a String specifying the index
     */
    public String getIndex() {
        return index.get();
    }
    /**
     * Gets the part number of particular Part object
     * @return a String specifying the part number
     */
    public String getPartNum() {
        return partNum.get();
    }
    /**
     * Gets the cage number of particular Part object
     * @return a String specifying the cage number
     */
    public String getCage() {
        return cage.get();
    }
    /**
     * Gets the description of particular Part object
     * @return a String specifying the description
     */
    public String getDescription() {
        return description.get();
    }
    /**
     * Gets the units of assembly of particular Part object
     * @return a String specifying the units of assembly
     */
    public String getUnits() {
        return units.get();
    }
    /**
     * Gets the usable on code of particular Part object
     * @return a String specifying the usable on code
     */
    public String getUsable() {
        return usable.get();
    }
    /**
     * Gets the SMR code of particular Part object
     * @return a String specifying the SMR code
     */
    public String getSmr() {
        return smr.get();
    }

    /**
     * Sets the index
     * @param index the Part's index
     */
    public void setIndex(String index){
        this.index.set(index);
    }

    /**
     * Sets the part number
     * @param partNum  the Part's part number
     */
    public void setPartNum(String partNum){
        this.partNum.set(partNum);
    }

    /**
     * Sets the cage number
     * @param cage the Part's cage number
     */
    public void setCage(String cage){
        this.cage.set(cage);
    }

    /**
     * Sets the description
     * @param description the Part's description
     */
    public void setDescription(String description){
        this.description.set(description);
    }

    /**
     * Sets the units of assembly
     * @param units the Part's units of assembly
     */
    public void setUnits(String units){
        this.units.set(units);
    }

    /**
     * Sets the usable on code
     * @param usable the Part's usable on code
     */
    public void setUsable(String usable){
        this.usable.set(usable);
    }

    /**
     * Sets the SMR code
     * @param smr the Part's SMR Code
     */
    public void setSmr (String smr) {this.smr.set(smr);}

    /**
     * Prints specified object's index
     * @return a String specifying the index
     */
    @Override
    public String toString() {
        return index.get();
    }

}
