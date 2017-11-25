package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    private CurrencyCollection currencyCollection;
    private ParseXMLDocument parseXMLDocument;
    private DownloadXML downloander;
    private Calculations calculations;

    @FXML
    private TabPane tabPane;

    @FXML
    private TextField amountField;

    @FXML
    private ComboBox<String> calculateFromBox;

    @FXML
    private ComboBox<String> calculateToBox;

    @FXML
    private ComboBox<String> calculateByBox;

    @FXML
    private Button calculateButton;

    @FXML
    private Button genarateChartButton;

    @FXML
    private ComboBox<String> currencyBox;

    @FXML
    private ComboBox<String> rateTypeBox;

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
        currencyCollection.addElementToCollection(new Currency("polski złoty", 1, "PLN", 1,1,1));
        calculations = new Calculations(currencyCollection);
    }

    private void initOverview()
    {
        currencyObservableList.addAll(currencyCollection.getCurrencyList());
        overviewList.setItems(currencyObservableList);
    }

    private void initComboBoxes()
    {
        for(int i = 0; i < currencyCollection.getCollectionSize(); ++i)
        {
            calculateFromBox.getItems().add(currencyCollection.getCollectionElementByPosition(i).getCode() +  " - " + currencyCollection.getCollectionElementByPosition(i).getCurrencyName());
            calculateToBox.getItems().add(currencyCollection.getCollectionElementByPosition(i).getCode() + " - " + currencyCollection.getCollectionElementByPosition(i).getCurrencyName());
            currencyBox .getItems().add(currencyCollection.getCollectionElementByPosition(i).getCode() + " - " + currencyCollection.getCollectionElementByPosition(i).getCurrencyName());

        }
        calculateFromBox.setValue(currencyCollection.getCollectionElementByPosition(0).getCode() +  " - " + currencyCollection.getCollectionElementByPosition(0).getCurrencyName());
        calculateToBox.setValue(currencyCollection.getCollectionElementByPosition(0).getCode() +  " - " + currencyCollection.getCollectionElementByPosition(0).getCurrencyName());
        currencyBox.setValue(currencyCollection.getCollectionElementByPosition(0).getCode() +  " - " + currencyCollection.getCollectionElementByPosition(0).getCurrencyName());

        calculateByBox.getItems().addAll("Kurs średni", "Kurs kupna", "Kurs sprzedaży");
        calculateByBox.setValue("Kurs średni");

        rateTypeBox.getItems().addAll("Kurs średni", "Kurs kupna", "Kurs sprzedaży");
        rateTypeBox.setValue("Kurs średni");
        }

     private RateType checkRateType()
     {
         if(calculateByBox.getValue().equals("Kurs średni"))
             return RateType.AVERAGE_RATE;
         else if(calculateByBox.getValue().equals("Kurs kupna"))
             return RateType.PURCHASE_RATE;
         else
             return RateType.SELL_RATE;
     }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initOverview();
        initComboBoxes();

        calculateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Double result = 0.0;
                RateType rateType = checkRateType();
                String currencyFrom = calculateFromBox.getValue().substring(0, 3);
                String currencyTo = calculateToBox.getValue().substring(0, 3);
                if (currencyCollection.getCurrencyElementByCode(currencyFrom).getRate(rateType) == 0 || currencyCollection.getCurrencyElementByCode(currencyTo).getRate(rateType) == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Brak dostępnego typu kursu dla jednej z walut. Sprawdź dostępne kursy w zakładce: Przegląd ", ButtonType.OK);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.OK) {
                        alert.close();
                    }
                } else {
                    try {
                        result = calculations.calculateTransaction(currencyFrom, currencyTo, Double.parseDouble(amountField.getText()), rateType);

                        Alert alert = new Alert(Alert.AlertType.NONE, amountField.getText() + " " + currencyFrom + "  =  " + result.toString() + " " + currencyTo, ButtonType.OK);
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.OK) {
                            alert.close();
                        }

                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Wprowadz poprawną kwotę. ", ButtonType.OK);
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.OK) {
                            alert.close();
                        }
                    }
                }
            }
        });
    }
}
