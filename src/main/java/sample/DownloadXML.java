package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static java.lang.System.exit;

/**
 * Created by sojer on 20.11.2017.
 */
public class DownloadXML {
    private String urlAddress;

    public DownloadXML(String urlAddress)
    {
        this.urlAddress = urlAddress;
    }

    private void showAlert(String alertMessage, Alert.AlertType alertType)
    {
        Alert alert = new Alert(alertType, alertMessage, ButtonType.OK);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            exit(1);
        }
    }


    public Document GetXMLDocument()
    {
        Document doc = null;
        InputStream is = null;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URL url = new URL(urlAddress);
            is = url.openStream();
            doc = db.parse(is);
        }
        catch (Exception e) {
            showAlert("Internet connection required ! ", Alert.AlertType.CONFIRMATION);

        }
        finally {

            try {
                if (is != null) is.close();
            } catch (IOException ioe) {}
        }

        return doc;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }
}
