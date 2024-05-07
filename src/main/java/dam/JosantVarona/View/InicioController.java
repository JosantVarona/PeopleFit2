package dam.JosantVarona.View;

import dam.JosantVarona.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InicioController extends Controller implements Initializable {
    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object response) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private void goTologin() throws IOException {
        System.out.println(Scenes.LOGIN);
        App.currentController.changeScene(Scenes.LOGIN,null);
    }
    @FXML
    private void goToRegister() throws IOException {
        System.out.println(Scenes.REGISTER);
        App.currentController.changeScene(Scenes.REGISTER,null);
    }
}
