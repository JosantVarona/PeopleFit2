package dam.JosantVarona.View;

import dam.JosantVarona.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController extends Controller implements Initializable {
    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object response) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Change Scene Login
     * @throws IOException
     */
    @FXML
    private void goTologin() throws IOException {
        App.currentController.changeScene(Scenes.LOGIN,null);
    }

    /**
     * Change Scene Register
     * @throws IOException
     */
    @FXML
    private void goToRegister() throws IOException {

        App.currentController.changeScene(Scenes.REGISTER,null);
    }

    /**
     * Change Scene Delete User
     * @throws IOException
     */
    @FXML
    private void goToDelete() throws IOException {

        App.currentController.changeScene(Scenes.DELETEUSER,null);
    }

}
