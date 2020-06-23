package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML TableView<PartInfo> tableView;
    @FXML TableColumn<PartInfo, String> figureColumn;
    @FXML TableColumn<PartInfo, String> indexColumn;
    @FXML TableColumn<PartInfo, String> cageColumn;
    @FXML TableColumn<PartInfo, String> descriptionColumn;
    @FXML TableColumn<PartInfo, String> unitsColumn;
    @FXML TableColumn<PartInfo, String> usableColumn;
    @FXML TableColumn<PartInfo, String> SMRColumn;


    @Override
    public void initialize (URL url, ResourceBundle rb){
        figureColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("figure"));
        indexColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("index"));
        cageColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("cage"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("description"));
        unitsColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("units"));
        usableColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("usable"));
        SMRColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("smr"));

        tableView.setItems(getPartInfo());
    }

    public ObservableList<PartInfo> getPartInfo(){
        ObservableList<PartInfo> parts = FXCollections.observableArrayList();
        parts.add(new PartInfo("12", "13", "1234", "lamp", "1", "A", "123"));
        return parts;
    }
}
