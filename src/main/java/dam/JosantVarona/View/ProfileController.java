package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.DAO.UsuarioDAO;
import dam.JosantVarona.Model.Entity.Routine;
import dam.JosantVarona.Model.Entity.User;
import dam.JosantVarona.Model.Entity.UserSesion;
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
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileController extends Controller implements Initializable {
    @FXML
    Button volver;
    @FXML
    private TableView<Routine> tableView;
    @FXML
    private TableColumn<Routine, Integer> columnId;
    @FXML
    private TableColumn<Routine, String> Columname;
    @FXML
    private TableColumn<Routine, Dia> columnDia;
    @FXML
    private TextField nombre;
    private ObservableList<Routine> rutinas;
    /**
     * Loads the routines associated with the currently logged-in user and displays them in a TableView.
     *
     * @param input The input data, not used in this implementation.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void onOpen(Object input) throws IOException {
        User usuario = UserSesion.getInstancia().getUsuarioIniciado();
        List<Routine> rutinas= RutinaDAO.build().findByUsuario(usuario);
        this.rutinas = FXCollections.observableArrayList(rutinas);
        tableView.setItems(this.rutinas);
    }

    @Override
    public void onClose(Object response) {

    }
    /**
     * Initializes the TableView columns with appropriate cell value factories to display routine data.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnId.setCellValueFactory(Rutina -> new SimpleIntegerProperty(Rutina.getValue().getId()).asObject());
        Columname.setCellValueFactory(Rutina -> new SimpleStringProperty(Rutina.getValue().getName()));
        columnDia.setCellValueFactory(Rutina -> new SimpleObjectProperty<Dia>(Rutina.getValue().getDay()));
    }

    /**
     * Change Scene Beginning
     * @throws IOException
     */
    public void gotoMain() throws IOException {
        App.currentController.changeScene(Scenes.BEGINNING,null);
    }

    /**
     * Change Scene Show
     * going Routine Select
     * @throws IOException
     */
    @FXML
    private void exerciseR() throws IOException {
        Routine rutina = tableView.getSelectionModel().getSelectedItem();
        App.currentController.openModalv(Scenes.SHOW,"Mostrando Ejercicos ...",this,rutina);
    }

    /**
     * Update the Username in database
     */
    @FXML
    private void updatename(){
        if (User.validarnombre(nombre.getText())) {
            UserSesion.getInstancia().getUsuarioIniciado().setName(nombre.getText());
            UsuarioDAO us = new UsuarioDAO();
            us.save(UserSesion.getInstancia().getUsuarioIniciado());
        }else {
            AppController.invaliddata();
        }
    }
}
