package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.Entity.Rutina;
import dam.JosantVarona.Model.Entity.UserSesion;
import dam.JosantVarona.Model.Entity.Usuario;
import dam.JosantVarona.Model.Enum.Dia;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MrutinaController extends Controller implements Initializable {
    @FXML
    private Button volver;
    @FXML
    private TableView<Rutina> tableView;
    @FXML
    private TableColumn<Rutina, Integer> columnId;
    @FXML
    private TableColumn<Rutina, Dia> columnDia;
    @FXML
    private TableColumn<Rutina, String> columnname;
    @FXML
    private TableColumn<Rutina, Date> columFecha;
    @FXML
    private TableColumn<Rutina, Boolean> Eliminar;
    @FXML
    private Button Borra;
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
        columnDia.setCellValueFactory(Rutina -> new SimpleObjectProperty<Dia>(Rutina.getValue().getDia()));
        columnname.setCellValueFactory(Rutina -> new SimpleStringProperty(Rutina.getValue().getDescripcion()));
        //columFecha.setCellValueFactory(Rutina -> );
        Eliminar.setCellValueFactory(cellData ->{
            SimpleBooleanProperty selectedProperty = new SimpleBooleanProperty(cellData.getValue().getEliminar());
            selectedProperty.addListener((obs, oldValue, newValue) -> {
                System.out.println("Selected state changed to: " + newValue);
                cellData.getValue().setEliminar(newValue);

            });
            return selectedProperty;
        });
        Eliminar.setCellFactory(column -> {
            CheckBoxTableCell<Rutina, Boolean> cell = new CheckBoxTableCell<>();
            return cell;
        });
        Eliminar.setOnEditCommit((TableColumn.CellEditEvent<Rutina, Boolean> event) -> {
            Rutina item = event.getRowValue();

            Boolean nuevoValor = event.getNewValue();

            System.out.println("Nuevo valor: " + nuevoValor);

        });
        Eliminar.setEditable(true);
    }
    public void gotoMain() throws IOException {
        App.currentController.changeScene(Scenes.Pricipal,null);
    }
    @FXML
    private void agregarEjer() throws IOException {
        Rutina rutina = tableView.getSelectionModel().getSelectedItem();
       App.currentController.changeScene(Scenes.EDIT,rutina);
    }
    @FXML
    private void Eliminar() throws IOException {
        for (Rutina rutina : rutinas){
            if (rutina.getEliminar()==true){
                try {
                    RutinaDAO.build().delete(rutina);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        App.currentController.changeScene(Scenes.MODIFICAR,null);
    }
}
