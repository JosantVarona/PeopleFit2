package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.EjercicioDAO;
import dam.JosantVarona.Model.Entity.Ejercicio;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EjercicosController extends Controller implements Initializable {
    @FXML
    private ImageView volver;
    @FXML
    private TextField nameEjer;
    @FXML
    private Slider series;
    @FXML
    private Button masrepes;
    @FXML
    private Button menosrepes;

    @FXML
    private Label rep;
    @FXML
    private int nrepes =1;
    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object response) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void gotoRutina() throws IOException {
        App.currentController.changeScene(Scenes.CrearRutina,null);
    }
    @FXML
    public Ejercicio datosEjercicio(){
        Ejercicio result = null;
        String name= nameEjer.getText();
        int serie = (int) series.getValue();
        int repe = nrepes;
        if(validarname(name)){
            Ejercicio aux = new Ejercicio(serie,repe,name);
            result = aux;
        }

        return result;
    }
    public void guardarEjercicio() throws IOException {
        Ejercicio resulst = datosEjercicio();
        if(resulst != null) {

            EjercicioDAO e = new EjercicioDAO();
            e.save(resulst);
            App.currentController.changeScene(Scenes.CrearRutina, null);
        }else {
            AppController.ejercicioInvalido();
        }
    }
    public void aumentar(){
        nrepes++;
        rep.setText(nrepes+"");
    }
    public void disminuir(){
        if(nrepes > 1){
            nrepes--;
        }else {
            AppController.Numernegativo();
        }
        rep.setText(nrepes+"");
    }
    private boolean validarname(String name){
        boolean valido = false;
        if(name.matches("[a-zA-Z\\\\s]+")){
            valido = true;
        }
        return valido;
    }
}
