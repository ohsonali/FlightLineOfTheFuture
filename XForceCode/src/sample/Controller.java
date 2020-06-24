package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;



import java.net.URL;
import java.util.ArrayList;
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
        TableColumn<PartInfo, Void> colBtn = new TableColumn("Button Column");

        tableView.setRowFactory(tv -> {
            TableRow<PartInfo> row = new TableRow<>();
            row.setOnMouseEntered(event -> {
                if (! row.isEmpty()) {
                    //&& event.getButton()== MouseButton.PRIMARY && event.getClickCount() == 2

                    PartInfo clickedRow = row.getItem();
                    //printRow(clickedRow);
                    System.out.println(clickedRow.getFigure());
                }
            });
            return row ;
        });

        Callback<TableColumn<PartInfo, Void>, TableCell<PartInfo, Void>> cellFactory = new Callback<TableColumn<PartInfo, Void>, TableCell<PartInfo, Void>>() {
            @Override
            public TableCell<PartInfo, Void> call(final TableColumn<PartInfo, Void> param) {
                final TableCell<PartInfo, Void> cell = new TableCell<PartInfo, Void>() {

                    private final Button btn = new Button("Purchase");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            PartInfo PartInfo = getTableView().getItems().get(getIndex());
                            System.out.println("selectedPartInfo: " + PartInfo);  //what does the button does
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        //TableColumn SMRColumn = new TableColumn("smr");
        tableView.getColumns().addAll(figureColumn, indexColumn, cageColumn, descriptionColumn, unitsColumn, usableColumn, colBtn);


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
        parts.add(new PartInfo("123", "13", "1234", "lamp", "1", "A"));
        return parts;
    }
}
