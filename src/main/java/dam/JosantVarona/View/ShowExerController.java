package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.MultimediaDAO;
import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.Entity.Exercise;
import dam.JosantVarona.Model.Entity.Multimedia;
import dam.JosantVarona.Model.Entity.Routine;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowExerController extends Controller implements Initializable {
    @FXML
    private TableView<Exercise> tableView;
    @FXML
    private TableColumn<Exercise, Integer> columnId;
    @FXML
    private TableColumn<Exercise, Integer> columnSeries;
    @FXML
    private TableColumn<Exercise, Integer> columnRepes;
    @FXML
    private TableColumn<Exercise, String> columnName;
    private ObservableList<Exercise> ejercicioR;

    /**
     * Select Routine screen for exercise
     * @param input
     * @throws IOException
     */
    @Override
    public void onOpen(Object input) throws IOException {
        Routine rutina = (Routine) input;
        List<Exercise> ejercicios= RutinaDAO.build().findEjercicios(rutina);
        this.ejercicioR = FXCollections.observableArrayList(ejercicios);
        tableView.setItems(this.ejercicioR);
    }

    @Override
    public void onClose(Object response) {

    }

    /**
     * Show the Exercise for Routine
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnId.setCellValueFactory(Ejercicio -> new SimpleIntegerProperty(Ejercicio.getValue().getId()).asObject());
        columnName.setCellValueFactory(Ejercicio -> new SimpleStringProperty(Ejercicio.getValue().getName()));
        columnSeries.setCellValueFactory(Ejercicio -> new SimpleIntegerProperty(Ejercicio.getValue().getSerie()).asObject());
        columnRepes.setCellValueFactory(Ejercicio -> new SimpleIntegerProperty(Ejercicio.getValue().getRepes()).asObject());
    }

    /**
     * Show Multimedia fot exercise if you have, it does not have screen Error
     * @throws IOException
     */
    @FXML
    private void multiexer() throws IOException {
        MultimediaDAO m = new MultimediaDAO();
        Exercise exercise = tableView.getSelectionModel().getSelectedItem();
        Integer exis= exercise.getId();
        Multimedia aux = m.findByid(exis);
        if (aux != null) {
            if (aux.getType().matches("Foto")) {
                App.currentController.openModalv(Scenes.SHOWMULTI, "Mostrando", this, exercise);
            }else {
                //pantalla de la imagen
                App.currentController.openModalv(Scenes.SHOWVIDEO,"Mostrar",this,exercise);
            }
        }else {
            AppController.notMultimedia();
        }
    }

}
