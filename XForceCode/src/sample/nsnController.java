package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class nsnController implements Initializable {
    @FXML
    TableView<NSN> tableView;
    @FXML
    Text partNumber, partDescription, cage;

    @Override
    public void initialize (URL url, ResourceBundle rb){
        partNumber.setText(Part.getCurrentPart().getPart());
        partDescription.setText(Part.getCurrentPart().getDescription());
        cage.setText(Part.getCurrentPart().getCage());


        TableColumn nsnColumn = new TableColumn("NSN");
        TableColumn descriptionColumn = new TableColumn("Description");
        TableColumn cageColumn = new TableColumn("Cage");
        TableColumn<NSN, Void> colBtn = new TableColumn("Button Column");

        tableView.setRowFactory(tv -> {
            TableRow<NSN> row = new TableRow<>();
            row.setOnMouseEntered(event -> {
                if (!row.isEmpty()) {
                    //&& event.getButton()== MouseButton.PRIMARY && event.getClickCount() == 2

                    NSN clickedRow = row.getItem();
                    //printRow(clickedRow);
                    System.out.println("green");
                }
            });
            return row ;
        });

        Callback<TableColumn<NSN, Void>, TableCell<NSN, Void>> cellFactory = new Callback<TableColumn<NSN, Void>, TableCell<NSN, Void>>() {
            @Override
            public TableCell<NSN, Void> call(final TableColumn<NSN, Void> param) {
                final TableCell<NSN, Void> cell = new TableCell<NSN, Void>() {

                    private final Button btn = new Button("Confirm NSN");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            NSN nsn = getTableView().getItems().get(getIndex());
                            try {
                                openUserWindow(nsn, event);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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


        tableView.getColumns().addAll(nsnColumn, descriptionColumn, cageColumn, colBtn);


        nsnColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("nsn"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("description"));
        cageColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("cage"));

        tableView.setItems(getNSNInfo());
    }

    public ObservableList<NSN> getNSNInfo(){
        ObservableList<NSN> nsns = FXCollections.observableArrayList();

        for(int i = 0; i < ParsedInfo.nsn.size(); i++){
            String nsn = ParsedInfo.nsn.get(i)[0];
            String description = ParsedInfo.nsn.get(i)[1];
            String cage = ParsedInfo.nsn.get(i)[2];

            nsns.add(new NSN(nsn, description, cage));
        }
        return nsns;
    }

    public void openUserWindow(NSN nsn, ActionEvent e) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("UserWindow.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

}
