package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.Entity.IntanceRutina;
import dam.JosantVarona.Model.Entity.UserSesion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

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
    @FXML
    private ImageView salir;
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
    public void goToInicio() throws IOException{
        UserSesion.getInstancia().logOut();
        App.currentController.changeScene(Scenes.INICIO,null);
    }
}
