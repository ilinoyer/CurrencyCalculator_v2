import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import sample.CurrencyCollection;
import org.w3c.dom.Document;
import sample.ParseXMLDocument;
import sample.RateType;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class ParseXMLTest {

    CurrencyCollection currencyCollection;
    Document averageXML;
    Document purchaseSellXML;
    DocumentBuilderFactory dbf;
    DocumentBuilder db;

    @Before
    public void init()
    {
        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            currencyCollection = new CurrencyCollection();
            averageXML = db.parse(new File("D:\\Programowanie\\CurrencyCalculator_v2\\src\\test\\CurrencyAverage.xml"));
            purchaseSellXML = db.parse(new File("D:\\Programowanie\\CurrencyCalculator_v2\\src\\test\\CurrencyPurchaseSell.xml"));
        }
        catch (ParserConfigurationException | IOException | SAXException e ) {
            e.printStackTrace();
        }
    }

    @Test
    public void averageXMLParseTest()
    {
        ParseXMLDocument parseXMLDocument = new ParseXMLDocument(currencyCollection,averageXML);
        parseXMLDocument.CreateCurrencyCollection();
        assertThat(2,is(currencyCollection.getCollectionSize()));
    }

    @Test
    public void addPurchaseAndSellCourseTest()
    {
        ParseXMLDocument parseXMLDocument = new ParseXMLDocument(currencyCollection,averageXML);
        parseXMLDocument.CreateCurrencyCollection();
        parseXMLDocument.setXMLDoc(purchaseSellXML);
        parseXMLDocument.AddPurchaseAndSellCourse();
        assertThat(currencyCollection.getCollectionElementByPosition(0).getSellRate(),is(3.4998));
    }

    @Test
    public void collectAverageRateToChartsTest()
    {
        ParseXMLDocument parseXMLDocument = new ParseXMLDocument(currencyCollection,averageXML);
        assertThat(parseXMLDocument.collectDataToCharts("AUD", RateType.AVERAGE_RATE),is(2.7071));
    }

    @Test
    public void collectSellRateToChartsTest()
    {
        ParseXMLDocument parseXMLDocument = new ParseXMLDocument(currencyCollection,purchaseSellXML);
        assertThat(parseXMLDocument.collectDataToCharts("AUD", RateType.SELL_RATE),is(2.7586));
    }

    @Test
    public void collectPurchaseRateToChartTest()
    {
        ParseXMLDocument parseXMLDocument = new ParseXMLDocument(currencyCollection,purchaseSellXML);
        assertThat(parseXMLDocument.collectDataToCharts("AUD", RateType.PURCHASE_RATE),is(2.7040));
    }
}
