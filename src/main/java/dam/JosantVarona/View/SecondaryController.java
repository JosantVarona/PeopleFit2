package dam.JosantVarona.View;

import java.io.IOException;

import dam.JosantVarona.App;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("View/primary");
    }
}