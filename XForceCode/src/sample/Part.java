package sample;

import javafx.beans.property.SimpleStringProperty;

public class Part {
    private SimpleStringProperty index, partNum, cage, description, units, usable, smr;


    public Part(String index, String partNum, String cage, String description, String units, String usable, String smr){
        this.index = new SimpleStringProperty(index);
        this.partNum = new SimpleStringProperty(partNum);
        this.cage = new SimpleStringProperty(cage);
        this.description = new SimpleStringProperty( description);
        this.units = new SimpleStringProperty(units);
        this.usable = new SimpleStringProperty(usable);
        this.smr = new SimpleStringProperty(smr);
    }
    public String getIndex() {
        return index.get();
    }
    public String getPartNum() {
        return partNum.get();
    }
    public String getCage() {
        return cage.get();
    }
    public String getDescription() {
        return description.get();
    }
    public String getUnits() {
        return units.get();
    }
    public String getUsable() {
        return usable.get();
    }
    public String getSmr() {
        return smr.get();
    }

    public void setIndex(String index){
        this.index.set(index);
    }
    public void setPartNum(String partNum){
        this.partNum.set(partNum);
    }
    public void setCage(String cage){
        this.cage.set(cage);
    }
    public void setDescription(String description){
        this.description.set(description);
    }
    public void setUnits(String units){
        this.units.set(units);
    }
    public void setUsable(String usable){
        this.usable.set(usable);
    }
    public void setSmr (String smr) {this.smr.set(smr);}

    @Override
    public String toString() {
        return index.get();
    }

}
