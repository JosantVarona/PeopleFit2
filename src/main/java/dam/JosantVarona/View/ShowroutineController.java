package dam.JosantVarona.View;

import dam.JosantVarona.App;
import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.Entity.Routine;
import dam.JosantVarona.Model.Entity.UserSesion;
import dam.JosantVarona.Model.Entity.User;
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

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.cell.TextFieldTableCell;

public class ShowroutineController extends Controller implements Initializable {
    @FXML
    private Button volver;
    @FXML
    private TableView<Routine> tableView;
    @FXML
    private TableColumn<Routine, Integer> columnId;
    @FXML
    private TableColumn<Routine, Dia> columnDia;
    @FXML
    private TableColumn<Routine, String> columnname;
    @FXML
    private TableColumn<Routine, String> columFecha;
    @FXML
    private TableColumn<Routine, Boolean> Eliminar;
    @FXML
    private Button Borra;
    private ObservableList<Routine> rutinas;

    /**
     * Initializes the view with routines associated with the logged-in user.
     *
     * @param input The input object (not used in this method).
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
     * Show Routine fo User
     * you can change the name
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        columnDia.setCellValueFactory(cellData -> new SimpleObjectProperty<Dia>(cellData.getValue().getDay()));
        columnname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        columnname.setCellFactory(TextFieldTableCell.forTableColumn());
        columnname.setOnEditCommit(event ->{
            if (event.getNewValue()== event.getOldValue()){
                return;
            }
            if (event.getNewValue().matches(".{1,25}")){
                Routine rutina = event.getRowValue();
                rutina.setName(event.getNewValue());
                RutinaDAO.build().save(rutina);
            }else {
                AppController.routineInvaliddata();
            }
        });
        columFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateR().toString()) );
        Eliminar.setCellValueFactory(cellData ->{
            SimpleBooleanProperty selectedProperty = new SimpleBooleanProperty(cellData.getValue().getDeleteR());
            selectedProperty.addListener((obs, oldValue, newValue) -> {
                System.out.println("Selected state changed to: " + newValue);
                cellData.getValue().setDeleteR(newValue);

            });
            return selectedProperty;
        });
        Eliminar.setCellFactory(column -> {
            CheckBoxTableCell<Routine, Boolean> cell = new CheckBoxTableCell<>();
            return cell;
        });
        Eliminar.setOnEditCommit((TableColumn.CellEditEvent<Routine, Boolean> event) -> {
            Routine item = event.getRowValue();

            Boolean nuevoValor = event.getNewValue();

            System.out.println("Nuevo valor: " + nuevoValor);

        });
        Eliminar.setEditable(true);
    }

    /**
     * return Main page
     * @throws IOException
     */
    public void gotoMain() throws IOException {
        App.currentController.changeScene(Scenes.BEGINNING,null);
    }

    /**
     * Change Scene Edit with routine select
     * @throws IOException
     */
    @FXML
    private void agregarEjer() throws IOException {
        Routine rutina = tableView.getSelectionModel().getSelectedItem();
       App.currentController.changeScene(Scenes.EDIT,rutina);
    }

    /**
     * Delete routine database
     * @throws IOException
     */
    @FXML
    private void Eliminar() throws IOException {
        for (Routine rutina : rutinas){
            if (rutina.getDeleteR()==true){
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
