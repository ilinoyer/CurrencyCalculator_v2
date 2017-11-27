package sample;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ChartController implements Initializable{

    private ArrayList<String> listOfFiles;

    public ChartController()
    {
        this.listOfFiles = new ArrayList<>();
    }


    public void loadDirTest()
    {
        try {
            URL url = new URL("http://www.nbp.pl/kursy/xml/dir.txt");
            Scanner s = new Scanner(url.openStream());
            while( s.hasNext() )
                listOfFiles.add(s.nextLine());
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

        for(int i = 0 ; i < listOfFiles.size(); ++ i)
        {
            System.out.println(listOfFiles.get(i));
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
