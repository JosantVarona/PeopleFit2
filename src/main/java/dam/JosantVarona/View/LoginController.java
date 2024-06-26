package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.UsuarioDAO;
import dam.JosantVarona.Model.Entity.UserSesion;
import dam.JosantVarona.Model.Entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {
    @FXML
    private TextField Texcuenta;
    @FXML
    private PasswordField Texpass;
    @FXML
    private ImageView arrow;

    /**
     * Collect data for the User
     * Check if the data is correct
     * @return User
     * @throws IOException
     */
    @FXML
    public User Collectdata() throws IOException{
        User result = null;
        String correo = Texcuenta.getText();
        String pass = Texpass.getText();
        User aux = searchAccount(correo);
        String contrasena = User.segurity(pass);
        if (aux !=null){
            if (aux.getAccount().equals(correo) && aux.getPass().equals(contrasena) ){
                result = aux;
                UserSesion.getInstancia().logIn(result);
            }
        }
        return result;
    }

    /**
     *Controls the login process by collecting user data, verifying it, and redirecting to the appropriate scene.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void controlLogin() throws IOException {
        if(Collectdata() == null) {
            AppController.alerNotFound();
        } else {
            UserSesion.getInstancia().logIn(Collectdata());
            App.currentController.changeScene(Scenes.BEGINNING,null);

        }
    }

    /**
     * Delete User
     * Entering your account and password
     * @throws IOException
     */
    public void deleteUser() throws IOException{
        if(Collectdata() == null) {
            AppController.alerNotFound();
        } else {
            UserSesion.getInstancia().logOut();
            UsuarioDAO us = new UsuarioDAO();
            try {
                us.delete(Collectdata());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            App.currentController.changeScene(Scenes.START,null);
        }
    }
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
     * return to the home screen
     * @throws IOException
     */
    public void gotoInicio() throws IOException {
        System.out.println(Scenes.START);
        App.currentController.changeScene(Scenes.START,null);
    }

    /**
     * Find the Account
     * @param cuenta User
     * @return
     */
    private User searchAccount(String cuenta){
        User result= null;
        UsuarioDAO u = new UsuarioDAO();
        if (cuenta != null){
            User aux = u.findByid(cuenta);
            if (aux.getId() !=null){
                result = aux;
            }
        }
        return result;
    }

}
