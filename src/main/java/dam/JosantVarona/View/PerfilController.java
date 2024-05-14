package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.Entity.Rutina;
import dam.JosantVarona.Model.Entity.IntanceRutina;
import dam.JosantVarona.Model.Entity.UserSesion;
import dam.JosantVarona.Model.Entity.Usuario;
import dam.JosantVarona.Model.Enum.Dia;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PerfilController extends Controller implements Initializable {
    @FXML
    Button volver;
    @FXML
    private TableView<Rutina> tableView;
    @FXML
    private TableColumn<Rutina, Integer> columnId;
    @FXML
    private TableColumn<Rutina, String> Columname;
    @FXML
    private TableColumn<Rutina, Dia> columnDia;
    private ObservableList<Rutina> rutinas;
    @Override
    public void onOpen(Object input) throws IOException {
        Usuario usuario = UserSesion.getInstancia().getUsuarioIniciado();
        List<Rutina> rutinas= RutinaDAO.build().findByUsuario(usuario);
        this.rutinas = FXCollections.observableArrayList(rutinas);
        tableView.setItems(this.rutinas);
    }

    @Override
    public void onClose(Object response) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnId.setCellValueFactory(Rutina -> new SimpleIntegerProperty(Rutina.getValue().getId()).asObject());
        Columname.setCellValueFactory(Rutina -> new SimpleStringProperty(Rutina.getValue().getDescripcion()));
        columnDia.setCellValueFactory(Rutina -> new SimpleObjectProperty<Dia>(Rutina.getValue().getDia()));
    }
    public void gotoMain() throws IOException {
        App.currentController.changeScene(Scenes.Pricipal,null);
    }
    @FXML
    private void Ejercicios() throws IOException {
        Rutina rutina = tableView.getSelectionModel().getSelectedItem();
        App.currentController.openModalv(Scenes.MOSTRAR,"Mostrando Ejercicos ...",this,rutina);
    }
}
