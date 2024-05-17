package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.Entity.UserSesion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class beginningController extends Controller implements Initializable {
    @FXML
    private Button perfil;
    @FXML
    private Button create;
    @FXML
    private Button modify;
    @FXML
    private ImageView exit;
    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object response) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        perfil.setText(UserSesion.getInstancia().getUsuarioIniciado().getName());
    }
    public void goToProfile() throws IOException{
        App.currentController.changeScene(Scenes.PROFILE,null);
    }
    public void goToModify() throws IOException{
        App.currentController.changeScene(Scenes.MODIFICAR,null);
    }
    public void goToCrear() throws IOException{
        App.currentController.changeScene(Scenes.CREATEROUTINE,null);
    }
    public void goToInicio() throws IOException{
        UserSesion.getInstancia().logOut();
        App.currentController.changeScene(Scenes.START,null);
    }
}
