package dam.JosantVarona.View;

import dam.JosantVarona.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EjercicosController extends Controller implements Initializable {
    @FXML
    ImageView volver;
    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object response) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void gotoRutina() throws IOException {
        App.currentController.changeScene(Scenes.CrearRutina,null);
    }
}
