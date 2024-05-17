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
    @Override
    public void onOpen(Object input) throws IOException {
        List<Exercise> ejercicios = RutinaDAO.build().FindAllEjer();
        this.ejercicios = FXCollections.observableArrayList(ejercicios);
        tableView.setItems(this.ejercicios);
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
    public void gotoMain() throws IOException {
        App.currentController.changeScene(Scenes.BEGINNING,null);
    }
    public void goToExercise() throws IOException {
        App.currentController.changeScene(Scenes.EXERCISE,null);
    }
    @FXML
    public Routine dataRoutine(){
        Routine routine = new Routine();
        String nameR = name.getText();
        if (name.getText() !=null) {
            Routine aux = new Routine();
            aux.setName(nameR);
            aux.setDay(Dia.valueOf(dayR.getValue().toUpperCase()));
            aux.setExercises(selectExercise());
            aux.setUser(UserSesion.getInstancia().getUsuarioIniciado());
            routine = aux;
        }
        return routine;
    }
    private ArrayList<Exercise> selectExercise(){
        ArrayList<Exercise> exerciseSelect = new ArrayList<>();
        for (Exercise ejercicio : ejercicios){
            if (ejercicio.getAdd()==true){
                exerciseSelect.add(ejercicio);
            }
        }
        return exerciseSelect;
    }
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
    @FXML
    private void editExercise() throws IOException {
        Exercise ejercicio = tableView.getSelectionModel().getSelectedItem();
        App.currentController.openModalv(Scenes.EXERCISE,"actualizar Exercise",this,ejercicio);
    }
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
    @FXML
    private void findRoutine() throws IOException {
        Exercise ejercicio = tableView.getSelectionModel().getSelectedItem();
        App.currentController.openModalv(Scenes.ROUTINE,"Rutinas que pertenece",this,ejercicio);
    }
}
