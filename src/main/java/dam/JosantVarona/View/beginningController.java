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
    /**
     * nick User
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        perfil.setText(UserSesion.getInstancia().getUsuarioIniciado().getName());
    }

    /**
     * Change Scene Profile
     * @throws IOException
     */
    public void goToProfile() throws IOException{
        App.currentController.changeScene(Scenes.PROFILE,null);
    }

    /**
     * Change Scene Modify Routine
     * @throws IOException
     */
    public void goToModify() throws IOException{
        App.currentController.changeScene(Scenes.MODIFICAR,null);
    }

    /**
     * Change Scene Create Routine
     * @throws IOException
     */
    public void goToCrear() throws IOException{
        App.currentController.changeScene(Scenes.CREATEROUTINE,null);
    }

    /**
     * Change Scene go Inicio
     * @throws IOException
     */
    public void goToInicio() throws IOException{
        UserSesion.getInstancia().logOut();
        App.currentController.changeScene(Scenes.START,null);
    }
}
