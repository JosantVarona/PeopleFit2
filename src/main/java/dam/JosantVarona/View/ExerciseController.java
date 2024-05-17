package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.EjercicioDAO;
import dam.JosantVarona.Model.Entity.Exercise;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExerciseController extends Controller implements Initializable {
    @FXML
    private ImageView volver;
    @FXML
    private TextField nameEjer;
    @FXML
    private Slider series;
    @FXML
    private Button butrepes;
    @FXML
    private Button lessrepes;

    @FXML
    private Label rep;
    @FXML
    private int nrepes = 1;
    private boolean verifica = false;
    private Exercise exerciseupdate = null;

    @Override
    public void onOpen(Object input) throws IOException {
        exerciseupdate = (Exercise) input;
        if(exerciseupdate != null){
            volver.setOpacity(0);
        }
    }

    @Override
    public void onClose(Object response) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void gotoRutina() throws IOException {
        App.currentController.changeScene(Scenes.CREATEROUTINE, null);
    }

    @FXML
    public Exercise dataExer() {
        Exercise result = null;
        if (exerciseupdate == null) {

            String name = nameEjer.getText();
            int serie = (int) series.getValue();
            int repe = nrepes;
            if (validatename(name)) {
                Exercise aux = new Exercise(serie, repe, name);
                result = aux;
            }
        } else {
            if (validatename(nameEjer.getText())) {
                exerciseupdate.setName(nameEjer.getText());
                exerciseupdate.setSerie((int) series.getValue());
                exerciseupdate.setRepes(nrepes);
            }
            result = exerciseupdate;
        }

        return result;
    }

    public void saveExer() throws IOException {
        Exercise resulst = dataExer();
        if (resulst != null) {

            EjercicioDAO e = new EjercicioDAO();
            e.save(resulst);
            App.currentController.changeScene(Scenes.CREATEROUTINE, null);
        } else {
            AppController.exerInvaliddata();
        }
    }

    public void increcrement() {
        nrepes++;
        rep.setText(nrepes + "");
    }

    public void diminish() {
        if (nrepes > 1) {
            nrepes--;
        } else {
            AppController.Numernegativo();
        }
        rep.setText(nrepes + "");
    }

    private boolean validatename(String name) {
        boolean valido = false;
        if (name.matches("[a-zA-Z\\\\s]+")) {
            valido = true;
        }
        return valido;
    }

    @FXML
    private void closeWindow(Event event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}

