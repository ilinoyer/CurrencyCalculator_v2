package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import static java.lang.System.exit;

public class AlertBox {
    public static void showAlert(String alertMessage, Alert.AlertType alertType)
    {
        Alert alert = new Alert(alertType, alertMessage, ButtonType.OK);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            exit(1);
        }
    }
}
