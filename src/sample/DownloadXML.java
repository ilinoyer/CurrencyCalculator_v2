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
    private String urlAddres;

    public DownloadXML(String urlAddres)
    {
        this.urlAddres = urlAddres;
    }

    public Document GetXMLDocument()
    {
        Document doc = null;
        InputStream is = null;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URL url = new URL(urlAddres);
            is = url.openStream();
            doc = db.parse(is);
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Internet connection required ! ", ButtonType.YES);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                exit(1);
            }
        }
        finally {

            try {
                if (is != null) is.close();
            } catch (IOException ioe) {}
        }

        return doc;
    }

    public void setUrlAddres(String urlAddres) {
        this.urlAddres = urlAddres;
    }
}
