package dam.JosantVarona.View;

import dam.JosantVarona.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContollerLayaut extends Controller implements Initializable {
    @Override
    public void onOpen(Object input) {

    }

    @Override
    public void onClose(Object response) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Scene Start
     * @throws IOException
     */
    @FXML
    private void goToStart() throws IOException{
        App.currentController.changeScene(Scenes.START,null);
    }

}
