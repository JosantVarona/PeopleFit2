package dam.JosantVarona.View;

import java.io.IOException;

import dam.JosantVarona.App;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("View/secondary");
    }
}
