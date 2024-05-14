package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.UsuarioDAO;
import dam.JosantVarona.Model.Entity.IntanceRutina;
import dam.JosantVarona.Model.Entity.UserSesion;
import dam.JosantVarona.Model.Entity.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {
    @FXML
    private TextField Texcuenta;
    @FXML
    private TextField Texpass;
    @FXML
    private ImageView flecha;
    @FXML
    public Usuario RecogerDatos() throws IOException{
        Usuario result = null;
        String correo = Texcuenta.getText();
        String pass = Texpass.getText();
        Usuario aux = buscaCuenta(correo);
        if (aux !=null){
            if (aux.getCuenta().equals(correo) && aux.getPass().equals(pass)){
                result = aux;
                UserSesion.getInstancia().logIn(result);
            }
        }
        return result;
    }
    public void controlLogin() throws IOException {
        if(RecogerDatos() == null) {
            AppController.alernoEncontrada();
        } else {
            UserSesion.getInstancia().logIn(RecogerDatos());
            App.currentController.changeScene(Scenes.Pricipal,null);

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
    public void gotoInicio() throws IOException {
        System.out.println(Scenes.INICIO);
        App.currentController.changeScene(Scenes.INICIO,null);
    }
    private Usuario buscaCuenta(String cuenta){
        Usuario result= null;
        UsuarioDAO u = new UsuarioDAO();
        if (cuenta != null){
            Usuario aux = u.findByid(cuenta);
            if (aux.getId() !=null){
                result = aux;
            }
        }
        return result;
    }
}
