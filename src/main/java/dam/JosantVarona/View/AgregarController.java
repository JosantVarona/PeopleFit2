package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.Entity.Ejercicio;
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

public class AgregarController extends Controller implements Initializable {
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
    private Button agregado;

    private ObservableList<Ejercicio> ejercicios;
    @Override
    public void onOpen(Object input) throws IOException {
        System.out.println(IntanceRutina.getInstancia().getRutinaLogin());
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
    }
    @FXML
    public void agregar() throws IOException {
        for (Ejercicio ejercicio : ejercicios){
            if (ejercicio.getAnadir()==true){
                IntanceRutina.getInstancia().getRutinaLogin().addEjercicio(ejercicio);
            }

        }App.currentController.changeScene(Scenes.EDIT,IntanceRutina.getInstancia().getRutinaLogin());
    }
    @FXML
    private void closeWindow(Event event){
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
