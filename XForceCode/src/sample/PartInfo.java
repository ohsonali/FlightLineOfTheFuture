package sample;

import javafx.beans.property.SimpleStringProperty;

public class PartInfo {
    private SimpleStringProperty figure, index, cage, description, units, usable;


    public PartInfo(String figure, String index, String cage, String description, String units, String usable){
        this.figure = new SimpleStringProperty(figure);
        this.index = new SimpleStringProperty(index);
        this.cage = new SimpleStringProperty(cage);
        this.description = new SimpleStringProperty( description);
        this.units = new SimpleStringProperty(units);
        this.usable = new SimpleStringProperty(usable);
        //this.smr = new SimpleStringProperty(smr);
    }
    public String getFigure() {
        return figure.get();
    }
    public String getIndex() {
        return index.get();
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
    /*public String getSMR() {
        return smr.get();
    }*/

    public void setFigure(String figure){
        this.figure.set(figure);
    }
    public void setIndex(String index){
        this.index.set(index);
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
    @Override
    public String toString() {
        return figure.get();
    }
    /*public void setSmr(String smr){
        this.smr.set(smr);
    }*/
}
