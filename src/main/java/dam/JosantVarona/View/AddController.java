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
    /**
     * Handles the actions to be performed when the view is opened.
     * It fetches all exercises from the database, removes the exercises already assigned to the current routine,
     * and sets the remaining exercises to be displayed in the TableView.
     *
     * @param input The input object, which is not used in this method.
     * @throws IOException If an I/O error occurs during the process.
     */
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
    /**
     * Initializes the TableView columns with appropriate cell value factories and cell factories.
     * Sets up the columns to display exercise details and allows editing the "Add" column with a CheckBox.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
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
    /**
     * Adds selected exercises to the current routine and updates the database.
     * If an exercise is marked as "Add" (checkbox selected), it is added to the current routine.
     * Then, the routine is updated in the database.
     * Finally, the scene is changed to the edit scene.
     *
     * @throws IOException If an I/O error occurs during scene change.
     */
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
    /**
     * Event close window
     */
    private void closeWindow(Event event){
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
