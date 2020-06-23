package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML TableView<PartInfo> tableView;
    /*@FXML TableColumn<PartInfo, String> figureColumn;
    @FXML TableColumn<PartInfo, String> indexColumn;
    @FXML TableColumn<PartInfo, String> cageColumn;
    @FXML TableColumn<PartInfo, String> descriptionColumn;
    @FXML TableColumn<PartInfo, String> unitsColumn;
    @FXML TableColumn<PartInfo, String> usableColumn;
    @FXML TableColumn<PartInfo, String> SMRColumn;*/

    public void Clicked() {
        System.out.println("a");
    }

    @Override
    public void initialize (URL url, ResourceBundle rb){

        TableColumn figureColumn = new TableColumn("Figure");
        TableColumn indexColumn = new TableColumn("Index");
        TableColumn cageColumn = new TableColumn("Cage");
        TableColumn descriptionColumn = new TableColumn("Description");
        TableColumn unitsColumn = new TableColumn("Units");
        TableColumn usableColumn = new TableColumn("Usable");
        TableColumn actionColumn = new TableColumn("Action");

        actionColumn.setCellFactory(param->  new TableCell<String, String>() {

            final Button btn = new Button("Purchase");

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    btn.setOnAction(event->Clicked());
                    setGraphic(btn);
                    setText(null);
                }
            }
        });

        //TableColumn SMRColumn = new TableColumn("smr");
        tableView.getColumns().addAll(figureColumn, indexColumn, cageColumn, descriptionColumn, unitsColumn, usableColumn, actionColumn);


        figureColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("figure"));
        indexColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("index"));
        cageColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("cage"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("description"));
        unitsColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("units"));
        usableColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("usable"));

        tableView.setItems(getPartInfo());
    }

    public ObservableList<PartInfo> getPartInfo(){
        ObservableList<PartInfo> parts = FXCollections.observableArrayList();
        parts.add(new PartInfo("12", "13", "1234", "lamp", "1", "A"));
        return parts;
    }
}
