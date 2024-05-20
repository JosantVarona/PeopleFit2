package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.Entity.Exercise;
import dam.JosantVarona.Model.Entity.IntanceRutina;
import dam.JosantVarona.Model.Entity.Routine;
import dam.JosantVarona.Model.Enum.Dia;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UpDateController extends  Controller implements Initializable {
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
    private TableColumn<Exercise, Boolean> Anadio;
    @FXML
    private Button delete;
    @FXML
    private Button add;
    @FXML
    private ComboBox<String> diaR;
    @FXML
    private ImageView revert;
    private ObservableList<Exercise> ejercicioB;
    /**
     * Initializes the view with exercises associated with the provided routine.
     *
     * @param input The input object representing the routine to be displayed.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void onOpen(Object input) throws IOException {
        Routine rutina = (Routine) input;
        asignar(rutina);
        IntanceRutina.getInstancia().logR(rutina);
        List<Exercise> ejercicios = RutinaDAO.build().findEjercicios(rutina);
        this.ejercicioB = FXCollections.observableArrayList(ejercicios);
        tableView.setItems(this.ejercicioB);
    }

    @Override
    public void onClose(Object response) {

    }

    /**
     * Show Exercise for Routine Select
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnId.setCellValueFactory(Ejercicio -> new SimpleIntegerProperty(Ejercicio.getValue().getId()).asObject());
        columnName.setCellValueFactory(Ejercicio -> new SimpleStringProperty(Ejercicio.getValue().getName()));
        columnSeries.setCellValueFactory(Ejercicio -> new SimpleIntegerProperty(Ejercicio.getValue().getSerie()).asObject());
        columnRepes.setCellValueFactory(Ejercicio -> new SimpleIntegerProperty(Ejercicio.getValue().getRepes()).asObject());

        Anadio.setCellValueFactory(cellData -> {
            SimpleBooleanProperty selectedProperty = new SimpleBooleanProperty(cellData.getValue().getAdd());
            selectedProperty.addListener((obs, oldValue, newValue) -> {
                System.out.println("Selected state changed to: " + newValue);
                cellData.getValue().setAdd(newValue);
            });
            return selectedProperty;
        });
        Anadio.setCellFactory(column -> {
            CheckBoxTableCell<Exercise, Boolean> cell = new CheckBoxTableCell<>();
            return cell;
        });
        Anadio.setOnEditCommit((TableColumn.CellEditEvent<Exercise, Boolean> event) -> {
            Exercise item = event.getRowValue();

            Boolean nuevoValor = event.getNewValue();

            System.out.println("Nuevo valor: " + nuevoValor);


        });
        Anadio.setEditable(true);
        diaR.setItems(FXCollections.observableArrayList("Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"));
        diaR.setValue("Ninguno");
    }

    /**
     * Remove exercise for routine
     * @throws IOException
     */
    @FXML
    public void delete() throws IOException {
        for (Exercise ejercicio :ejercicioB){
            if(ejercicio.getAdd()==true){
                IntanceRutina.getInstancia().getRutinaLogin().removerEjercicio(ejercicio);
            }
        }RutinaDAO.build().asociaEjercicios(IntanceRutina.getInstancia().getRutinaLogin());
        App.currentController.changeScene(Scenes.EDIT,IntanceRutina.getInstancia().getRutinaLogin());
    }

    /**
     * Change Scene add
     * @throws IOException
     */
    @FXML
    public void addExercise() throws IOException {
        App.currentController.openModalv(Scenes.ADD,"Añadir Ejercicios",this,null);
    }

    /**
     * Change Scene Modificar
     * Close instace Routine
     * @throws IOException
     */
    @FXML
    public void goBack() throws IOException{
        App.currentController.changeScene(Scenes.MODIFICAR,null);
        IntanceRutina.getInstancia().logOut();
    }

    /**
     * Update the day for Routine
     */
    @FXML
    public void updateDay(){
        if (Dia.valueOf(diaR.getValue().toUpperCase()) != Dia.NINGUNO){
            IntanceRutina.getInstancia().getRutinaLogin().setDay(Dia.valueOf(diaR.getValue().toUpperCase()));
            RutinaDAO.build().save(IntanceRutina.getInstancia().getRutinaLogin());
        }
    }

    /**
     * Update Exercise Routine
     * @param rutina
     */
    private void asignar(Routine rutina){
        rutina.setExercises(RutinaDAO.build().findEjercicios(rutina));
    }
}
