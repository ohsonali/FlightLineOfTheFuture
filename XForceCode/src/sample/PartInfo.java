package sample;

import javafx.beans.property.SimpleStringProperty;

public class PartInfo {
    private SimpleStringProperty figure, index, cage, description, units, usable, smr;


    public PartInfo(String figure, String index, String cage, String description, String units, String usable, String smr){
        this.figure = new SimpleStringProperty(figure);
        this.index = new SimpleStringProperty(index);
        this.cage = new SimpleStringProperty(cage);
        this.description = new SimpleStringProperty( description);
        this.units = new SimpleStringProperty(units);
        this.usable = new SimpleStringProperty(usable);
        this.smr = new SimpleStringProperty(smr);
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
    public String getSMR() {
        return smr.get();
    }
}
