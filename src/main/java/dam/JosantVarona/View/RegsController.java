package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.UsuarioDAO;
import dam.JosantVarona.Model.Entity.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegsController extends Controller implements Initializable {
    @FXML
    TextField Textname;
    @FXML
    TextField Textcorreo;
    @FXML
    TextField Textpass;
    @FXML
    ImageView flecha;

    @FXML
    public Usuario RegocerDatos() throws IOException{
        Usuario result = null;
        String correo = Textcorreo.getText();
        String nombre =Textname.getText();
        String pass = Textpass.getText() ;
        if (exitecuenta(correo)){
            result = null;
        }else {
            if (Usuario.validarCorreo(correo) && Usuario.validarnombre(nombre) && Usuario.validarnombre(pass)){
                Usuario u = new Usuario(correo,nombre,pass);
                result = u;
            }
        }

        return result;
    }
    public void guardarDatos() throws IOException{
        Usuario result = RegocerDatos();
        UsuarioDAO uDAO = new UsuarioDAO();
        if(result !=null){
            uDAO.save(result);
            App.currentController.changeScene(Scenes.Pricipal,null);
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

    public void goToInicio() throws IOException{
        App.currentController.changeScene(Scenes.INICIO,null);
    }
    private boolean exitecuenta (String cuenta){
        boolean result= true;
        UsuarioDAO us = new UsuarioDAO();
        if(cuenta !=null){
            Usuario u = us.findByid(cuenta);
            if (u.getId()==null){
                result = false;
            }
        }
        return result;
    }
}
