package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.EjercicioDAO;
import dam.JosantVarona.Model.DAO.MultimediaDAO;
import dam.JosantVarona.Model.Entity.Exercise;
import dam.JosantVarona.Model.Entity.Multimedia;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExerciseController extends Controller implements Initializable {
    @FXML
    private VBox vBox;
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
    private Button multimedia;

    @FXML
    private Label rep;
    @FXML
    private int nrepes = 1;
    private boolean verifica = false;
    private Exercise exerciseupdate = null;
    private Integer multi;

    /**
     * Handles the opening of the view and performs initialization tasks based on the provided input.
     *
     * @param input The input object passed to the view when it is opened.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void onOpen(Object input) throws IOException {
        exerciseupdate = (Exercise) input;

        if(exerciseupdate != null){
            volver.setOpacity(0);
        }else {
            vBox.getChildren().remove(multimedia);
        }
    }

    @Override
    public void onClose(Object response) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Change Scene Create Routine
     * @throws IOException
     */
    public void gotoRutina() throws IOException {
        App.currentController.changeScene(Scenes.CREATEROUTINE, null);
    }

    /**
     * Collect data Exercise
     * @return
     */
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

    /**
     * save the Exercise
     * Change Scene Create Routine
     * @throws IOException
     */
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

    /**
     * increment repetitions the Exercise
     */
    public void increcrement() {
        nrepes++;
        rep.setText(nrepes + "");
    }

    /**
     * diminish the repetitions the Exercise
     * show screen
     */
    public void diminish() {
        if (nrepes > 1) {
            nrepes--;
        } else {
            AppController.Numernegativo();
        }
        rep.setText(nrepes + "");
    }

    /**
     * Validate name Exercise
     * @param name the Exercise
     * @return
     */
    private boolean validatename(String name) {
        boolean valido = false;
        if (!name.isEmpty() && name.length()<20) {
            valido = true;
        }
        return valido;
    }

    @FXML
    private void closeWindow(Event event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    @FXML
    /**
     * Option for the Multimedia for Exercise already inserted
     * Change Scene Multimedia
     */
    private void Mutimedia() throws IOException {
        MultimediaDAO m = new MultimediaDAO();
        Integer exis =exerciseupdate.getId();
        Multimedia aux = m.findByid(exis);
        if (aux==null) {
            App.currentController.openModalv(Scenes.MULTIMEDIA, "Añadir multimedia", this, exerciseupdate);
        }else {
            AppController.Error();
        }
        }

}

