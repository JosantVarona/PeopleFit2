package dam.JosantVarona.View;

import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.Entity.Exercise;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnId.setCellValueFactory(Ejercicio -> new SimpleIntegerProperty(Ejercicio.getValue().getId()).asObject());
        columnName.setCellValueFactory(Ejercicio -> new SimpleStringProperty(Ejercicio.getValue().getName()));
        columnSeries.setCellValueFactory(Ejercicio -> new SimpleIntegerProperty(Ejercicio.getValue().getSerie()).asObject());
        columnRepes.setCellValueFactory(Ejercicio -> new SimpleIntegerProperty(Ejercicio.getValue().getRepes()).asObject());
    }
}
