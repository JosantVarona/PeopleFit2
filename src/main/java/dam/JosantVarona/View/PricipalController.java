package dam.JosantVarona.View;

import dam.JosantVarona.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PricipalController extends Controller implements Initializable {
    @FXML
    private Button perfil;
    @FXML
    private Button Crear;
    @FXML
    private Button Modify;
    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object response) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void goToPerfil() throws IOException{
        App.currentController.changeScene(Scenes.PERFIL,null);
    }
    public void goToModify() throws IOException{
        App.currentController.changeScene(Scenes.MODIFICAR,null);
    }
    public void goToCrear() throws IOException{
        App.currentController.changeScene(Scenes.CrearRutina,null);
    }
}
