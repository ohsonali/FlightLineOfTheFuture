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
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Sample
 */
public class NSNController implements Initializable {
    @FXML
    TableView<NSN> tableView;
    @FXML
    Text partNumber, partDescription, cage;
    @FXML
    Button cancel;

    @Override
    public void initialize (URL url, ResourceBundle rb){
        partNumber.setText("Part Number: " + PartInfo.getCurrentPart().getPartNum().replaceAll("[#=]", "").trim());
        partDescription.setText("Description: " + PartInfo.getCurrentPart().getDescription().replaceAll("\\.", "").trim());
        cage.setText("Cage: " + PartInfo.getCurrentPart().getCage());


        TableColumn nsnColumn = new TableColumn("NSN");
        TableColumn descriptionColumn = new TableColumn("Description");
        TableColumn cageColumn = new TableColumn("Cage");
        TableColumn<NSN, Void> colBtn = new TableColumn("Button Column");
        TableColumn<NSN, Void> infoBtn = new TableColumn ("More Info");

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

        Callback<TableColumn<NSN, Void>, TableCell<NSN, Void>> cellFactory2 = new Callback<TableColumn<NSN, Void>, TableCell<NSN, Void>>() {
            @Override
            public TableCell<NSN, Void> call(final TableColumn<NSN, Void> param) {
                final TableCell<NSN, Void> cell2 = new TableCell<NSN, Void>() {

                    private final Button btn = new Button("Link");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            NSN nsn = getTableView().getItems().get(getIndex());
                            try {
                                openWeb("https://www.nsncenter.com/NSN/" + nsn.getNsn().trim());
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
                return cell2;
            }
        };
        infoBtn.setCellFactory(cellFactory2);

        tableView.getColumns().addAll(nsnColumn, descriptionColumn, cageColumn, colBtn, infoBtn);


        nsnColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("nsn"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("description"));
        cageColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("cage"));

        tableView.setItems(getNSNInfo());
    }

    public ObservableList<NSN> getNSNInfo(){
        ObservableList<NSN> nsns = FXCollections.observableArrayList();

        for(int i = 0; i < NSNScrape.getScrapedNSNs().size(); i++){
            String nsn = NSNScrape.getScrapedNSNs().get(i)[0];
            String description = NSNScrape.getScrapedNSNs().get(i)[1];
            String cage = NSNScrape.getScrapedNSNs().get(i)[2];

            nsns.add(new NSN(nsn, description, cage));
        }
        return nsns;
    }

    public void openUserWindow(NSN nsn, ActionEvent e) throws Exception {
        PartInfo.setCurrentNSN(nsn);
        Parent root = FXMLLoader.load(getClass().getResource("UserEntryController.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void openWeb(String url) {
        try {
            Desktop.getDesktop().browse(new URL(url).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancel (ActionEvent e) throws Exception {
        PartInfo.removePart();
        NSNScrape.clearNSNList();
        Parent root = FXMLLoader.load(getClass().getResource("MainController.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
