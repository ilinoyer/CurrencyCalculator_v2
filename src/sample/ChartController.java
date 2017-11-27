package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Scanner;

import static java.util.Calendar.YEAR;

public class ChartController implements Initializable{

    private ArrayList<String> listOfFiles;
    private ArrayList<Double> dataYList;
    private ArrayList<Double> dataXList;

    private ParseXMLDocument parseXMLDocument;
    private DownloadXML downloadXML;

    private RateType rateType;
    private int year;
    private int month;
    private String currencyCode;

    @FXML
    private LineChart<Double, Double> currencyChart;


    public ChartController(int year, int month, ParseXMLDocument parseXMLDocument, DownloadXML downloadXML, RateType rateType, String currencyCode)
    {
        this.listOfFiles = new ArrayList<>();
        this.dataYList = new ArrayList<>();
        this.dataXList = new ArrayList<>();
        this.parseXMLDocument = parseXMLDocument;
        this.downloadXML = downloadXML;
        this.rateType = rateType;
        this.year = year;
        this.month = month;
        this.currencyCode = currencyCode;
        loadDir(year);
        createChart();
    }


    public void loadDir(int year)
    {
        String addres;
        if(year != Calendar.getInstance().get(YEAR))
            addres = "http://www.nbp.pl/kursy/xml/dir" + year + ".txt";
        else
            addres = "http://www.nbp.pl/kursy/xml/dir.txt";

        try {
            URL url = new URL(addres);
            Scanner s = new Scanner(url.openStream());
            while( s.hasNext() )
                listOfFiles.add(s.nextLine());
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void createChart()
    {
        String tempYear = Integer.toString(year).substring(2,4);
        String tempMonth;

        if(month >= 10)
            tempMonth = Integer.toString(month);
        else
            tempMonth = "0" + Integer.toString(month);

        System.out.println(tempYear + tempMonth);

        for(int i = 0; i < listOfFiles.size(); ++i)
        {
            if(rateType == RateType.AVERAGE_RATE && listOfFiles.get(i).contains("a") && listOfFiles.get(i).contains("z" + tempYear + tempMonth))
            {
                System.out.println("jeste");
                downloadXML.setUrlAddres("http://www.nbp.pl/kursy/xml/" + listOfFiles.get(i) + ".xml");
                parseXMLDocument.setXMLDoc(downloadXML.GetXMLDocument());
                dataYList.add(parseXMLDocument.collectDataToCharts(currencyCode, rateType));
                dataXList.add(Double.parseDouble(listOfFiles.get(i).substring(9,11)));
                System.out.println(listOfFiles.get(i).substring(4,6) + "    http://www.nbp.pl/kursy/xml/" + listOfFiles.get(i) + ".xml");

            }
            else if((rateType == RateType.PURCHASE_RATE || rateType == RateType.SELL_RATE) && listOfFiles.get(i).contains("c") && listOfFiles.contains(tempYear + tempMonth))
            {
                downloadXML.setUrlAddres("http://www.nbp.pl/kursy/xml/" + listOfFiles.get(i) + ".xml");
                parseXMLDocument.setXMLDoc(downloadXML.GetXMLDocument());
                dataYList.add(parseXMLDocument.collectDataToCharts(currencyCode, rateType));
                dataXList.add(Double.parseDouble(listOfFiles.get(i).substring(9,11)));
            }
        }
    }

    public void initLineChart()
    {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        for(int i = 0 ; i < dataXList.size(); ++i)
        {
            series.getData().add(new XYChart.Data<>(dataXList.get(i), dataYList.get(i)));
            System.out.println("Dodano");
        }

        currencyChart.getData().add(series);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for(int i = 0; i < dataXList.size(); ++i)
        {
            System.out.println(dataXList.get(i) + "  " + dataYList.get(i));
        }

        initLineChart();
    }
}
