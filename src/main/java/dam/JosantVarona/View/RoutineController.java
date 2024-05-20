package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.EjercicioDAO;
import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.Entity.Exercise;
import dam.JosantVarona.Model.Entity.Routine;
import dam.JosantVarona.Model.Entity.UserSesion;
import dam.JosantVarona.Model.Enum.Dia;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RoutineController extends Controller implements Initializable {
    @FXML
    private ImageView arrow;
    @FXML
    private Button Ejercicios;
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
    private TableColumn<Exercise, Boolean> columnAdd;
    @FXML
    private TextField name;
    @FXML
    private ComboBox<String> dayR;
    @FXML
    private Button createRoutine;
    @FXML
    private ImageView delete;

    private ObservableList<Exercise> ejercicios;
    /**
     * Loads all exercises from the database and sets them in the TableView for display.
     *
     * @param input The input data, if any, provided when opening the view. Not used in this method.
     * @throws IOException If an I/O exception occurs while accessing the database or setting the TableView items.
     */
    @Override
    public void onOpen(Object input) throws IOException {
        List<Exercise> ejercicios = RutinaDAO.build().FindAllEjer();
        this.ejercicios = FXCollections.observableArrayList(ejercicios);
        tableView.setItems(this.ejercicios);
    }

    @Override
    public void onClose(Object response) {

    }
    /**
     * Initializes the TableView and ComboBox controls with appropriate cell value factories and items.
     * Sets up the columnAdd CheckBoxTableCell for editing and handling changes.

     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnId.setCellValueFactory(Ejercicio -> new SimpleIntegerProperty(Ejercicio.getValue().getId()).asObject());
        columnName.setCellValueFactory(Ejercicio -> new SimpleStringProperty(Ejercicio.getValue().getName()));
        columnSeries.setCellValueFactory(Ejercicio -> new SimpleIntegerProperty(Ejercicio.getValue().getSerie()).asObject());
        columnRepes.setCellValueFactory(Ejercicio -> new SimpleIntegerProperty(Ejercicio.getValue().getRepes()).asObject());

        columnAdd.setCellValueFactory(cellData -> {
            SimpleBooleanProperty selectedProperty = new SimpleBooleanProperty(cellData.getValue().getAdd());
            selectedProperty.addListener((obs, oldValue, newValue) -> {
                System.out.println("Selected state changed to: " + newValue);
                cellData.getValue().setAdd(newValue);
            });
            return selectedProperty;
        });
        columnAdd.setCellFactory(column -> {
            CheckBoxTableCell<Exercise, Boolean> cell = new CheckBoxTableCell<>();
            return cell;
        });
        columnAdd.setOnEditCommit((TableColumn.CellEditEvent<Exercise, Boolean> event) -> {
            Exercise item = event.getRowValue();

            Boolean nuevoValor = event.getNewValue();

            System.out.println("Nuevo valor: " + nuevoValor);


        });
        columnAdd.setEditable(true);
        dayR.setItems(FXCollections.observableArrayList("Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"));
        dayR.setValue("Ninguno");
    }

    /**
     * return main page
     * @throws IOException
     */
    public void gotoMain() throws IOException {
        App.currentController.changeScene(Scenes.BEGINNING,null);
    }

    /**
     * Change Scene Exercise
     * @throws IOException
     */
    public void goToExercise() throws IOException {
        App.currentController.changeScene(Scenes.EXERCISE,null);
    }

    /**
     * Collect data Routine together your exercise
     * @return Routine
     */
    @FXML
    public Routine dataRoutine(){
        Routine routine = new Routine();
        String nameR = name.getText();
        if (!name.getText().isEmpty() && name.getText().length()<25) {
            Routine aux = new Routine();
            aux.setName(nameR);
            aux.setDay(Dia.valueOf(dayR.getValue().toUpperCase()));
            aux.setExercises(selectExercise());
            aux.setUser(UserSesion.getInstancia().getUsuarioIniciado());
            routine = aux;
        }
        return routine;
    }

    /**
     * Selects exercises that have been marked for addition.
     *
     * @return An ArrayList containing Exercise objects that have been marked for addition.
     */
    private ArrayList<Exercise> selectExercise(){
        ArrayList<Exercise> exerciseSelect = new ArrayList<>();
        for (Exercise ejercicio : ejercicios){
            if (ejercicio.getAdd()==true){
                exerciseSelect.add(ejercicio);
            }
        }
        return exerciseSelect;
    }

    /**
     * Save Routine in database
     * @throws IOException page Error
     */
    public void saveRoutine() throws IOException {
        RutinaDAO r = new RutinaDAO();
        Routine rutina = dataRoutine();
        if (rutina != null && rutina.getDay()!=Dia.NINGUNO && rutina.getExercises().size() >1){
            r.save(rutina);
            App.currentController.changeScene(Scenes.BEGINNING,null);
        }else {
            AppController.routineInvaliddata();
        }
    }

    /**
     * Change Scene Exercise for edit
     * @throws IOException
     */
    @FXML
    private void editExercise() throws IOException {
        Exercise ejercicio = tableView.getSelectionModel().getSelectedItem();
        App.currentController.openModalv(Scenes.EXERCISE,"actualizar Exercise",this,ejercicio);
    }

    /**
     * Delete the exercise select
     * @throws IOException
     */
    @FXML
    public void deleteExercise() throws IOException{
        for (Exercise ejercicio : ejercicios){
            if (ejercicio.getAdd()== true){
                EjercicioDAO ejer = new EjercicioDAO();
                try {
                    ejer.delete(ejercicio);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }App.currentController.changeScene(Scenes.CREATEROUTINE,null);
    }

    /**
     * return the that belongs
     * @throws IOException
     */
    @FXML
    private void findRoutine() throws IOException {
        Exercise ejercicio = tableView.getSelectionModel().getSelectedItem();
        App.currentController.openModalv(Scenes.ROUTINE,"Rutinas que pertenece",this,ejercicio);
    }
}
