package sample;

import javafx.beans.property.SimpleStringProperty;

public class PartInfo {
    private SimpleStringProperty figure, part, cage, description, units, usable, smr;


    public PartInfo(String figure, String part, String cage, String description, String units, String usable, String smr){
        this.figure = new SimpleStringProperty(figure);
        this.part = new SimpleStringProperty(part);
        this.cage = new SimpleStringProperty(cage);
        this.description = new SimpleStringProperty( description);
        this.units = new SimpleStringProperty(units);
        this.usable = new SimpleStringProperty(usable);
        this.smr = new SimpleStringProperty(smr);
    }
    public String getFigure() {
        return figure.get();
    }
    public String getPart() {
        return part.get();
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

    public void setFigure(String figure){
        this.figure.set(figure);
    }
    public void setPart(String part){
        this.part.set(part);
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
        return figure.get();
    }

}
