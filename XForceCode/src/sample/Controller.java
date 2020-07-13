package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sample.ParsedInfo.figureNumber;

public class Controller implements Initializable {
    @FXML TableView<PartInfo> tableView;
    @FXML Text figure, figureDescription;
    /*@FXML TableColumn<PartInfo, String> figureColumn;
    @FXML TableColumn<PartInfo, String> indexColumn;
    @FXML TableColumn<PartInfo, String> cageColumn;
    @FXML TableColumn<PartInfo, String> descriptionColumn;
    @FXML TableColumn<PartInfo, String> unitsColumn;
    @FXML TableColumn<PartInfo, String> usableColumn;
    @FXML TableColumn<PartInfo, String> SMRColumn;*/

    @Override
    public void initialize (URL url, ResourceBundle rb){
        ParsedInfo.figureNumber = ParsedInfo.parts.get(0)[0];
        if (ParsedInfo.parts.get(0)[3].trim().length() == 0) {
            ParsedInfo.figureDescription = "Not Available";
        } else {ParsedInfo.figureDescription = ParsedInfo.parts.get(0)[3];}
        figure.setText("Figure: " + ParsedInfo.figureNumber);
        figureDescription.setText("Figure Description: " + ParsedInfo.figureDescription);


        TableColumn figureColumn = new TableColumn("Index");
        TableColumn partColumn = new TableColumn("Part Number");
        TableColumn cageColumn = new TableColumn("Cage");
        TableColumn descriptionColumn = new TableColumn("Description");
        TableColumn unitsColumn = new TableColumn("Units");
        TableColumn usableColumn = new TableColumn("Usable");
        TableColumn smrColumn = new TableColumn("SMR Code");
        TableColumn<PartInfo, Void> colBtn = new TableColumn("Button Column");

        tableView.setRowFactory(tv -> {
            TableRow<PartInfo> row = new TableRow<>();
            row.setOnMouseEntered(event -> {
                if (! row.isEmpty()) {
                    //&& event.getButton()== MouseButton.PRIMARY && event.getClickCount() == 2

                    PartInfo clickedRow = row.getItem();
                    //printRow(clickedRow);
                    System.out.println("green");
                }
            });
            return row ;
        });

        Callback<TableColumn<PartInfo, Void>, TableCell<PartInfo, Void>> cellFactory = new Callback<TableColumn<PartInfo, Void>, TableCell<PartInfo, Void>>() {
            @Override
            public TableCell<PartInfo, Void> call(final TableColumn<PartInfo, Void> param) {
                final TableCell<PartInfo, Void> cell = new TableCell<PartInfo, Void>() {

                    private final Button btn = new Button("Order");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            PartInfo partInfo = getTableView().getItems().get(getIndex());
                            try {
                                partClicked(partInfo, event);
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

        tableView.getColumns().addAll(figureColumn, partColumn, cageColumn, descriptionColumn, unitsColumn, usableColumn, smrColumn, colBtn);


        figureColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("figure"));
        partColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("part"));
        cageColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("cage"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("description"));
        unitsColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("units"));
        usableColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("usable"));
        smrColumn.setCellValueFactory(new PropertyValueFactory<PartInfo, String>("smr"));

        tableView.setItems(getPartInfo());
    }

    public ObservableList<PartInfo> getPartInfo(){
        ObservableList<PartInfo> parts = FXCollections.observableArrayList();

        for(int i = 1; i < ParsedInfo.parts.size(); i++){
            String figure = ParsedInfo.parts.get(i)[0];
            String partNumber = ParsedInfo.parts.get(i)[1];
            String cage = ParsedInfo.parts.get(i)[2];
            String description = ParsedInfo.parts.get(i)[3];
            String units = ParsedInfo.parts.get(i)[4];
            String usable = ParsedInfo.parts.get(i)[5];
            String smr = ParsedInfo.parts.get(i)[6];

            parts.add(new PartInfo(figure, partNumber, cage, description, units, usable, smr));

        }
        return parts;
    }

    public void partClicked(PartInfo part, ActionEvent e) throws Exception {
        Part.addPart(part);
        String partNumber = part.getPart().trim();
        Document doc = Jsoup.connect("https://www.nsncenter.com/NSNSearch?q=" + partNumber).get();
        Elements rows = doc.select("tr");
        for (Element row : rows ) {
            String[] entry = new String[3];
            Elements nsns = row.select("a[href][onclick^='dataLayer.push({'event':'trackEvent','eventCategory':'Commerce','eventAction':'ProductClick','eventLabel':']");
            Elements cages = row.select("a[href^='https://www.cagecode.info/']");
            // code assumes we will only find one nsns and one cages per row
            for (Element nsn : nsns) {
                entry[0] = nsn.text();
                Pattern descriptions = Pattern.compile(",'name':'(.*?)','category':'");
                Matcher matcher = descriptions.matcher(nsn.attr("onclick"));
                while (matcher.find()) {
                    entry[1] = matcher.group(1);
                }
            }
            String cageStrings = "";
            for (Element cage : cages) {
                cageStrings += " " + cage.text();
            }
            entry[2] = cageStrings;
            if (entry[0] != null) {
                ParsedInfo.nsn.add(entry);
            }
        }
        openNSNWindow(e);
    }


    public void openNSNWindow (ActionEvent e) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("nsn.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

}
