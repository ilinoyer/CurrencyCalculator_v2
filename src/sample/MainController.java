package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    private CurrencyCollection currencyCollection;
    private ParseXMLDocument parseXMLDocument;
    private DownloadXML downloander;
    private Calculations calculations;
    private ArrayList<String> months = new ArrayList<>();

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
    private ComboBox<String> monthBox;

    @FXML
    private ComboBox<String> yearBox;

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
        months.addAll(Arrays.asList("Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"));
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

        monthBox.getItems().addAll(months);
        monthBox.setValue("Styczeń");

        Calendar now = Calendar.getInstance();
        for(int i = 2010; i <= now.get(Calendar.YEAR); ++i)
            yearBox.getItems().add(Integer.toString(i));

        yearBox.setValue("2010");
    }

     private RateType checkRateType(ComboBox box)
     {
         if(box.getValue().equals("Kurs średni"))
             return RateType.AVERAGE_RATE;
         else if(box.getValue().equals("Kurs kupna"))
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
                RateType rateType = checkRateType(calculateByBox);
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

        genarateChartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Calendar now = Calendar.getInstance();
                RateType rateType = checkRateType(rateTypeBox);
                String currency = currencyBox.getValue().substring(0,3);
                if ( currencyCollection.getCurrencyElementByCode(currency).getRate(rateType) == 0 ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Brak dostępnego kursu dla tej waluty. Sprawdź dostępne kursy w zakładce: Przegląd ", ButtonType.OK);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.OK) {
                        alert.close();
                    }
                } else {
                    int monthNumber = months.indexOf(monthBox.getValue()) + 1;
                    int year = Integer.parseInt(yearBox.getValue());
                    if( !((now.get(Calendar.YEAR) == year) && (now.get(Calendar.MONTH) + 1 < monthNumber) ))
                    {
                        try
                        {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("ChartWindow.fxml"));
                            fxmlLoader.setController(new ChartController(year, monthNumber, parseXMLDocument, downloander, rateType, currency));
                            Scene scene = new Scene((Parent) fxmlLoader.load(), 1100, 500);
                            Stage stage = new Stage();
                            stage.setTitle("Chart Window");
                            stage.setScene(scene);
                            stage.show();

                        }catch(Exception e)
                        {
                            e.printStackTrace();
                        }

                    }

                }
            }
        });
    }
}
