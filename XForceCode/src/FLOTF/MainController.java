//File: MainController.java
//MainController class controls the MainController.fxml window
package FLOTF;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class controls the table display of parsed parts from the technical order PDF
 * @author Bernard Chan, Sonali Loomba
 */

public class MainController implements Initializable {
    /** fx id of TableView with row objects of type Part*/
    @FXML TableView<Part> tableView;
    /** fx id of Text displaying the parts' corresponding figure number */
    @FXML Text figure;
    /** fx id of Text displaying the parts' corresponding figure description */
    @FXML Text figureDescription;
    /** fx id of Button to open order history folder */
    @FXML Button orderHistory;


    /**
     * Initializes MainController.fxml window
     * Sets figure number and figure description for parts list
     * Creates rows with columns displaying index, part number, cage number, description, units of assembly,
     * usable on code, SMR code, and Order Button for each part
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize (URL url, ResourceBundle rb){
        processParsedTO();
        TOParser.setFigureNumber(TOParser.getParts().get(0)[0]);
        if (TOParser.getParts().get(0)[3].trim().length() == 0) {
            TOParser.setFigureDescription("Not Available");
        } else {TOParser.setFigureDescription(TOParser.getParts().get(0)[3]);}
        figure.setText("Figure: " + TOParser.getFigureNumber());
        figureDescription.setText("Figure Description: " + TOParser.getFigureDescription());


        TableColumn indexColumn = new TableColumn("Index");
        TableColumn partColumn = new TableColumn("Part Number");
        TableColumn cageColumn = new TableColumn("Cage");
        TableColumn descriptionColumn = new TableColumn("Description");
        TableColumn unitsColumn = new TableColumn("Units");
        TableColumn usableColumn = new TableColumn("Usable");
        TableColumn smrColumn = new TableColumn("SMR Code");
        TableColumn<Part, Void> colBtn = new TableColumn("Button Column");


        Callback<TableColumn<Part, Void>, TableCell<Part, Void>> cellFactory = new Callback<TableColumn<Part, Void>, TableCell<Part, Void>>() {
            @Override
            public TableCell<Part, Void> call(final TableColumn<Part, Void> param) {
                final TableCell<Part, Void> cell = new TableCell<Part, Void>() {

                    private final Button btn = new Button("Order");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Part Part = getTableView().getItems().get(getIndex());
                            try {
                                partClicked(Part, event);
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

        tableView.getColumns().addAll(indexColumn, partColumn, cageColumn, descriptionColumn, unitsColumn, usableColumn, smrColumn, colBtn);


        indexColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("index"));
        partColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partNum"));
        cageColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("cage"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("description"));
        unitsColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("units"));
        usableColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("usable"));
        smrColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("smr"));

        tableView.setItems(getPart());
    }
    /**
     * Creates a displayable list of all parts in technical order PDF from <code>TOParser.getParts()</code>
     * @return an ObservableList of type Part
     */
    public ObservableList<Part> getPart(){
        ObservableList<Part> tableParts = FXCollections.observableArrayList();

        for(int i = 1; i < TOParser.getParts().size(); i++){
            String index = TOParser.getParts().get(i)[0];
            String partNumber = TOParser.getParts().get(i)[1];
            String cage = TOParser.getParts().get(i)[2];
            String description = TOParser.getParts().get(i)[3].replaceAll("\\.", "").trim();
            String units = TOParser.getParts().get(i)[4];
            String usable = TOParser.getParts().get(i)[5];
            String smr = TOParser.getParts().get(i)[6];

            tableParts.add(new Part(index, partNumber, cage, description, units, usable, smr));

        }
        return tableParts;
    }

    /**
     * Stores user's chosen part in <code>PartInfo.currentPart</code>
     * Finds all possible matching NSNs for chosen part <code>NSNScrape.webScrape()</code>
     * Opens NSNController.fxml or confirmation window depending on number of matching NSNs
     *
     * @param part user's chosen part
     * @param e button clicked
     * @throws Exception if <code>NSNScrape.webScrape()</code> or <code>openUserWindow()</code> is unable to load
     */
    public void partClicked(Part part, ActionEvent e) throws Exception {
        PartInfo.setCurrentPart(part);
        NSNScrape.webScrape(part);
        if (NSNScrape.getScrapedNSNs().size() == 1 ){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "We only found one possible NSN on NSNCenter.com. Confirm " + NSNScrape.getScrapedNSNs().get(0)[0] + " ?", ButtonType.YES, ButtonType.CANCEL);

            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                String nsn = NSNScrape.getScrapedNSNs().get(0)[0];
                String description = NSNScrape.getScrapedNSNs().get(0)[1];
                String cage = NSNScrape.getScrapedNSNs().get(0)[2];
                PartInfo.setCurrentNSN(new NSN (nsn, description, cage));
                openUserWindow(e);
            } else {
                PartInfo.removePart();
                NSNScrape.clearNSNList();
            }
        } else if (NSNScrape.getScrapedNSNs().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "We found zero possible NSNs on NSNCenter.com. Would you still like to proceed?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                String nsn = "";
                String description = "";
                String cage = "";
                PartInfo.setCurrentNSN(new NSN (nsn, description, cage));
                openUserWindow(e);
            } else {
                PartInfo.removePart();
                NSNScrape.clearNSNList();
            }
        } else {
            openNSNWindow(e);
        }
    }


    /**
     * Opens NSNController.fxml
     * @param e button clicked and more than one matching NSN
     * @throws Exception if NSNController.fxml is unable to load
     */
    public void openNSNWindow (ActionEvent e) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("NSNController.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Opens UserEntryController.fxml
     * @param e button clicked and one or less matching NSNs
     * @throws Exception if UserEntryController.fxml is unable to load
     */
    public void openUserWindow (ActionEvent e) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("UserEntryController.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Removes empty rows from <code>TOParser.parts</code>
     * Separates multiple part numbers matching a single index
     */
    public void processParsedTO() {
        for (int i = 0; i <TOParser.getParts().size(); i++) {
            String index = TOParser.getParts().get(i)[0];
            String partNumber = TOParser.getParts().get(i)[1];
            String cage = TOParser.getParts().get(i)[2];
            String description = TOParser.getParts().get(i)[3];
            String units = TOParser.getParts().get(i)[4];
            String usable = TOParser.getParts().get(i)[5];
            String smr = TOParser.getParts().get(i)[6];
            String[] partLines = stringLines(partNumber);
            if (index.trim().length() == 0 && partNumber.trim().length() == 0 && cage.trim().length() == 0 && description.trim().length() == 0 &&
                    units.trim().length() == 0 && usable.trim().length() == 0 && smr.trim().length() == 0) {
                TOParser.getParts().remove(i);
            }

            if (partLines.length > 1) {
                String[] cageLines = stringLines(cage);
                String[] smrLines = stringLines(smr);
                for (int j = 0; j < partLines.length; j++) {
                    String[] newRow = new String[TOParser.getParts().get(i).length];
                    newRow[0] = index;
                    newRow[3] = description;
                    newRow[4] = units;
                    newRow[5] = usable;

                    newRow[1] = partLines[j].replace(" 4", " =");
                    newRow[2] = cageLines[j];
                    newRow[6] = smrLines[j];
                    TOParser.getParts().add(i + j + 1, newRow);
                }
                TOParser.getParts().remove(i);
            }
        }
    }

    /**
     * Splits text with multiple lines into separate Strings
     * @param str String to be split
     * @return array of split Strings
     */
    public static String[] stringLines(String str){
        return str.split("\r\n|\r|\n");
    }

    /**
     * Opens order history folder
     * @param e button clicked
     * @throws IOException if folder is unable to load
     */
    public void orderHistoryClicked (ActionEvent e) throws IOException {
        if (!Desktop.isDesktopSupported()) {
            System.out.println("Desktop Not Supported");
            System.exit(0);
        } else {
             Desktop.getDesktop().open(new File(System.getProperty("user.dir") + "/F9006 History"));
        }
    }
}
