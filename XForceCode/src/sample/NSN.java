package sample;

import javafx.beans.property.SimpleStringProperty;

public class NSN {
    private SimpleStringProperty nsn, description, cage;

    public NSN (String nsn, String description, String cage){
        this.nsn = new SimpleStringProperty(nsn);
        this.description = new SimpleStringProperty(description);
        this.cage = new SimpleStringProperty(cage);
    }

    public String getNsn() {
        return nsn.get();
    }
    public void setNSN(String nsn) {
        this.nsn.set(nsn);
    }

    public String getDescription() {
        return description.get();
    }
    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getCage() {
        return cage.get();
    }
    public void setCage(String cage) {
        this.cage.set(cage);
    }

    @Override
    public String toString(){
        return nsn.get();
    }
}

