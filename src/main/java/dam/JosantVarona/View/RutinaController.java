package dam.JosantVarona.View;

import dam.JosantVarona.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RutinaController extends Controller implements Initializable {
    @FXML
    ImageView flecha;
    @FXML
    Button Ejercicios;
    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object response) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void gotoMain() throws IOException {
        App.currentController.changeScene(Scenes.Pricipal,null);
    }
    public void gotoEjercico() throws IOException {
        App.currentController.changeScene(Scenes.EJERCICIOS,null);
    }
}
