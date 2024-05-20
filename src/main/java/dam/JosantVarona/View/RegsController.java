package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.UsuarioDAO;
import dam.JosantVarona.Model.Entity.User;
import dam.JosantVarona.Model.Entity.UserSesion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegsController extends Controller implements Initializable {
    @FXML
    private TextField Textname;
    @FXML
    private TextField Textcorreo;
    @FXML
    private PasswordField Textpass;
    @FXML
    private ImageView arrow;

    /**
     * Collect data the User
     * validate data User
      * @return User
     * @throws IOException
     */
    @FXML
    public User collecData() throws IOException{
        User result = null;
        String correo = Textcorreo.getText();
        String nombre =Textname.getText();
        String pass = Textpass.getText() ;
        if (existsAccount(correo)){
            result = null;
        }else {
            if (User.validarCorreo(correo) && User.validarnombre(nombre) && User.validarnombre(pass)){
                String contrasena = User.segurity(pass);
                User u = new User(correo,contrasena,nombre);
                result = u;
                UserSesion.getInstancia().logIn(result);
            }
        }

        return result;
    }

    /**
     * Save data User in database
     * @throws IOException Error register
     */
    public void saveData() throws IOException{
        User result = collecData();
        UsuarioDAO uDAO = new UsuarioDAO();
        if(result !=null){
            uDAO.save(result);
            UserSesion.getInstancia().logIn(result);
            App.currentController.changeScene(Scenes.BEGINNING,null);
        }else {
            AppController.alertaResgis();
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
     * return Start
     * @throws IOException
     */
    public void goToInicio() throws IOException{
        App.currentController.changeScene(Scenes.START,null);
    }

    /**
     *
     Check if the account exists in the database
     * @param account the user already inserted
     * @return User
     */
    private boolean existsAccount(String account){
        boolean result= true;
        UsuarioDAO us = new UsuarioDAO();
        if(account !=null){
            User u = us.findByid(account);
            if (u.getId()==null){
                result = false;
            }
        }
        return result;
    }
}
