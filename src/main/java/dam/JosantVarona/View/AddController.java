package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.Entity.Exercise;
import dam.JosantVarona.Model.Entity.IntanceRutina;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.Node;
import javafx.event.Event;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddController extends Controller implements Initializable {
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
    @FXML
    private TableColumn<Exercise, Boolean> columnadd;
    @FXML
    private Button add;


    private ObservableList<Exercise> exercises;
    @Override
    public void onOpen(Object input) throws IOException {
        List<Exercise> ejercicios = RutinaDAO.build().FindAllEjer();
        List<Exercise> ejerAsig = IntanceRutina.getInstancia().getRutinaLogin().getExercises();
        ejercicios.removeAll(ejerAsig);

        this.exercises = FXCollections.observableArrayList(ejercicios);
        tableView.setItems(this.exercises);
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

        columnadd.setCellValueFactory(cellData -> {
            SimpleBooleanProperty selectedProperty = new SimpleBooleanProperty(cellData.getValue().getAdd());
            selectedProperty.addListener((obs, oldValue, newValue) -> {
                System.out.println("Selected state changed to: " + newValue);
                cellData.getValue().setAdd(newValue);
            });
            return selectedProperty;
        });
        columnadd.setCellFactory(column -> {
            CheckBoxTableCell<Exercise, Boolean> cell = new CheckBoxTableCell<>();
            return cell;
        });
        columnadd.setOnEditCommit((TableColumn.CellEditEvent<Exercise, Boolean> event) -> {
            Exercise item = event.getRowValue();

            Boolean nuevoValor = event.getNewValue();

            System.out.println("Nuevo valor: " + nuevoValor);


        });
        columnadd.setEditable(true);
    }
    @FXML
    public void addExer() throws IOException {
        for (Exercise ejercicio : exercises){
            if (ejercicio.getAdd()==true){
                IntanceRutina.getInstancia().getRutinaLogin().addEjercicio(ejercicio);
            }
        }RutinaDAO.build().asociaEjercicios(IntanceRutina.getInstancia().getRutinaLogin());
        App.currentController.changeScene(Scenes.EDIT,IntanceRutina.getInstancia().getRutinaLogin());
    }
    @FXML
    private void closeWindow(Event event){
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
