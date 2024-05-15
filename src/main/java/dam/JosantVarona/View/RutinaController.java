package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.EjercicioDAO;
import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.Entity.Ejercicio;
import dam.JosantVarona.Model.Entity.Rutina;
import dam.JosantVarona.Model.Entity.IntanceRutina;
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

public class RutinaController extends Controller implements Initializable {
    @FXML
    private ImageView flecha;
    @FXML
    private Button Ejercicios;
    @FXML
    private TableView<Ejercicio> tableView;
    @FXML
    private TableColumn<Ejercicio, Integer> columnId;
    @FXML
    private TableColumn<Ejercicio, Integer> columnSeries;
    @FXML
    private TableColumn<Ejercicio, Integer> columnRepes;
    @FXML
    private TableColumn<Ejercicio, String> columnName;
    @FXML
    private TableColumn<Ejercicio, Boolean> Anadio;
    @FXML
    private TextField name;
    @FXML
    private ComboBox<String> diaR;
    @FXML
    private Button crearRutina;
    @FXML
    private ImageView basura;

    private ObservableList<Ejercicio> ejercicios;
    @Override
    public void onOpen(Object input) throws IOException {
        List<Ejercicio> ejercicios = RutinaDAO.build().FindAllEjer();
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

        Anadio.setCellValueFactory(cellData -> {
            SimpleBooleanProperty selectedProperty = new SimpleBooleanProperty(cellData.getValue().getAnadir());
            selectedProperty.addListener((obs, oldValue, newValue) -> {
                System.out.println("Selected state changed to: " + newValue);
                cellData.getValue().setAnadir(newValue);
            });
            return selectedProperty;
        });
        Anadio.setCellFactory(column -> {
            CheckBoxTableCell<Ejercicio, Boolean> cell = new CheckBoxTableCell<>();
            return cell;
        });
        Anadio.setOnEditCommit((TableColumn.CellEditEvent<Ejercicio, Boolean> event) -> {
            Ejercicio item = event.getRowValue();

            Boolean nuevoValor = event.getNewValue();

            System.out.println("Nuevo valor: " + nuevoValor);


        });
        Anadio.setEditable(true);
        diaR.setItems(FXCollections.observableArrayList("Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"));
        diaR.setValue("Ninguno");
    }
    public void gotoMain() throws IOException {
        App.currentController.changeScene(Scenes.Pricipal,null);
    }
    public void gotoEjercico() throws IOException {
        App.currentController.changeScene(Scenes.EJERCICIOS,null);
    }
    @FXML
    public Rutina datosRutina(){
        Rutina rutina = new Rutina();
        String nameR = name.getText();
        if (name.getText() !=null) {
            Rutina aux = new Rutina();
            aux.setDescripcion(nameR);
            aux.setDia(Dia.valueOf(diaR.getValue().toUpperCase()));
            aux.setEjercicios(selectEjercicios());
            aux.setUsuario(UserSesion.getInstancia().getUsuarioIniciado());
            rutina = aux;
        }
        return rutina;
    }
    private ArrayList<Ejercicio> selectEjercicios(){
        ArrayList<Ejercicio> ejerciciosSeleccionados = new ArrayList<>();
        for (Ejercicio ejercicio : ejercicios){
            if (ejercicio.getAnadir()==true){
                ejerciciosSeleccionados.add(ejercicio);
            }
        }
        return ejerciciosSeleccionados;
    }
    public void guardarRutina() throws IOException {
        RutinaDAO r = new RutinaDAO();
        Rutina rutina = datosRutina();
        if (rutina != null && rutina.getDia()!=Dia.NINGUNO && rutina.getEjercicios().size() >1){
            r.save(rutina);
            App.currentController.changeScene(Scenes.Pricipal,null);
        }else {
            AppController.datosRutinainvalidos();
        }
    }
    @FXML
    private void editEjercicio() throws IOException {
        Ejercicio ejercicio = tableView.getSelectionModel().getSelectedItem();
        App.currentController.openModalv(Scenes.EJERCICIOS,"actualizar Ejercicio",this,ejercicio);
    }
    @FXML
    public void eliminarEjer() throws IOException{
        for (Ejercicio ejercicio : ejercicios){
            if (ejercicio.getAnadir()== true){
                EjercicioDAO ejer = new EjercicioDAO();
                try {
                    ejer.delete(ejercicio);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }App.currentController.changeScene(Scenes.CrearRutina,null);
    }
    @FXML
    private void buscarRutinas() throws IOException {
        Ejercicio ejercicio = tableView.getSelectionModel().getSelectedItem();
        App.currentController.openModalv(Scenes.RUTINAS,"Rutinas que pertenece",this,ejercicio);
    }
}
