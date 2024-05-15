package dam.JosantVarona.View;

import dam.JosantVarona.Model.DAO.EjercicioDAO;
import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.Entity.Ejercicio;
import dam.JosantVarona.Model.Entity.Rutina;
import dam.JosantVarona.Model.Entity.Usuario;
import dam.JosantVarona.Model.Enum.Dia;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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

public class PerteneceController extends Controller implements Initializable {
    @FXML
    private TableView<Rutina> tableView;
    @FXML
    private TableColumn<Rutina, Integer> columnId;
    @FXML
    private TableColumn<Rutina, Dia> columnDia;
    @FXML
    private TableColumn<Rutina, String> columnname;
    @FXML
    private TableColumn<Rutina, String> id_usario;
    private ObservableList<Rutina> rutinas;
    @Override
    public void onOpen(Object input) throws IOException {
        Ejercicio ejercicio = (Ejercicio) input;
        List<Rutina> rutinas= EjercicioDAO.build().findRutina(ejercicio);
        this.rutinas = FXCollections.observableArrayList(rutinas);
        tableView.setItems(this.rutinas);
    }

    @Override
    public void onClose(Object response) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        columnDia.setCellValueFactory(cellData -> new SimpleObjectProperty<Dia>(cellData.getValue().getDia()));
        columnname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        id_usario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getName()));
    }
}
