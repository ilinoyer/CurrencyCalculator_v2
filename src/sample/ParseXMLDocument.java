package sample;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * Created by sojer on 20.11.2017.
 */
public class ParseXMLDocument{

    private CurrencyCollection collection;
    private Document XMLDoc;

    public ParseXMLDocument(CurrencyCollection collection, Document XMLDoc)
    {
        this.collection = collection;
        this.XMLDoc = XMLDoc;
    }

    public void CreateCurrencyCollection()
    {
        String currencyName;
        int converter;
        String code;
        String temp;
        double rateOfExchange;

        NodeList currencyNameNode = XMLDoc.getElementsByTagName("nazwa_waluty");
        NodeList converterNode = XMLDoc.getElementsByTagName("przelicznik");
        NodeList codeNode = XMLDoc.getElementsByTagName("kod_waluty");
        NodeList rateOfExchangeNode = XMLDoc.getElementsByTagName("kurs_sredni");

        for(int i = 0; i < currencyNameNode.getLength(); ++i )
        {
            currencyName = currencyNameNode.item(i).getTextContent();
            converter = parseInt(converterNode.item(i).getTextContent());
            code = codeNode.item(i).getTextContent();

            temp = rateOfExchangeNode.item(i).getTextContent();
            temp = temp.replace(",", ".");
            rateOfExchange = parseDouble(temp);

            collection.addElementToCollection(new Currency(currencyName, converter, code, rateOfExchange));
        }
    }

    public void AddPurchuaseAndSellCourse()
    {
        String code;
        String temp;
        double purchaseRate;
        double sellRate;

        NodeList codeNode = XMLDoc.getElementsByTagName("kod_waluty");
        NodeList purchaseRateNode = XMLDoc.getElementsByTagName("kurs_kupna");
        NodeList sellRateNode = XMLDoc.getElementsByTagName("kurs_sprzedazy");

        for(int i = 0; i < codeNode.getLength(); ++i )
        {

            code = codeNode.item(i).getTextContent();

            temp = purchaseRateNode.item(i).getTextContent();
            temp = temp.replace(",", ".");
            purchaseRate = parseDouble(temp);

            temp = sellRateNode.item(i).getTextContent();
            temp = temp.replace(",", ".");
            sellRate = parseDouble(temp);

            collection.modifyElementOfCollection(code, purchaseRate,sellRate);
        }
    }

    public void setXMLDoc(Document XMLDoc) {
        this.XMLDoc = XMLDoc;
    }

    public double collectDataToCharts(String currencyCode, RateType rateType )
    {
        NodeList codeNode = XMLDoc.getElementsByTagName("kod_waluty");
        NodeList rateNode;
        String code;
        String temp;
        double value = 0.0;

        if(rateType == RateType.AVERAGE_RATE)
            rateNode = XMLDoc.getElementsByTagName("kurs_sredni");
        else if(rateType == RateType.SELL_RATE)
            rateNode = XMLDoc.getElementsByTagName("kurs_kupna");
        else
            rateNode = XMLDoc.getElementsByTagName("kurs_sprzedazy");

        for(int i = 0; i < codeNode.getLength(); ++i )
        {

            code = codeNode.item(i).getTextContent();

            if( currencyCode.equals(code))
            {
                temp = rateNode.item(i).getTextContent();
                temp = temp.replace(",", ".");
                value = parseDouble(temp);
            }

        }

        return value;
    }
}
