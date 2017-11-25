package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    private CurrencyCollection currencyCollection;
    private ParseXMLDocument parseXMLDocument;
    private DownloadXML downloander;

    @FXML
    private TabPane tabPane;

    @FXML
    private TextField amount;

    @FXML
    private ComboBox<String> calculateFrom;

    @FXML
    private ComboBox<String> calculateTo;

    @FXML
    private ComboBox<String> calculateBy;

    @FXML
    private Button calculateButton;

    @FXML
    private ListView<Currency> overviewList;
    private ObservableList<Currency> currencyObservableList = FXCollections.observableArrayList();

    public MainController()
    {
        currencyCollection = new CurrencyCollection();
        downloander = new DownloadXML("https://www.nbp.pl/kursy/xml/lastA.xml");
        parseXMLDocument = new ParseXMLDocument(currencyCollection, downloander.GetXMLDocument());
        parseXMLDocument.CreateCurrencyCollection();
        downloander.setUrlAddres("https://www.nbp.pl/kursy/xml/lastC.xml");
        parseXMLDocument.setXMLDoc(downloander.GetXMLDocument());
        parseXMLDocument.AddPurchuaseAndSellCourse();
    }

    public void initOverview()
    {
        currencyObservableList.addAll(currencyCollection.getCurrencyList());
        overviewList.setItems(currencyObservableList);
        //overviewList.setStyle("list-cell");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    initOverview();
    }
}
