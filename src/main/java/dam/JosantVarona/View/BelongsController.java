package dam.JosantVarona.View;

import dam.JosantVarona.Model.DAO.EjercicioDAO;
import dam.JosantVarona.Model.Entity.Exercise;
import dam.JosantVarona.Model.Entity.Routine;
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

public class BelongsController extends Controller implements Initializable {
    @FXML
    private TableView<Routine> tableView;
    @FXML
    private TableColumn<Routine, Integer> columnId;
    @FXML
    private TableColumn<Routine, Dia> columnDia;
    @FXML
    private TableColumn<Routine, String> columnname;
    @FXML
    private TableColumn<Routine, String> columnid_usario;
    private ObservableList<Routine> rutinas;
    /**
     * This method is called when the view is opened.
     * It retrieves the routines associated with the provided exercise and sets them in the table view.
     *
     * @param input The input data, which should be an Exercise object.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void onOpen(Object input) throws IOException {
        Exercise exercise = (Exercise) input;
        List<Routine> routine= EjercicioDAO.build().findRutina(exercise);
        this.rutinas = FXCollections.observableArrayList(routine);
        tableView.setItems(this.rutinas);
    }

    @Override
    public void onClose(Object response) {

    }

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        columnDia.setCellValueFactory(cellData -> new SimpleObjectProperty<Dia>(cellData.getValue().getDay()));
        columnname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        columnid_usario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getName()));
    }
}
