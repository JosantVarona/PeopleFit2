package dam.JosantVarona.View;

import dam.JosantVarona.App;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController extends Controller implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    static Alert alerta = new Alert(Alert.AlertType.ERROR);
    private Controller centerController;
    @Override
    public void onOpen(Object input) throws IOException {
        changeScene(Scenes.START,null);

    }

    @Override
    public void onClose(Object response) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public static View loadFXML(Scenes scenes) throws IOException {
        String url = scenes.getUrl();
        FXMLLoader loader = new FXMLLoader(App.class.getResource(url));
        Parent p = loader.load();
        Controller c = loader.getController();
        View view = new View();
        view.scene=p;
        view.controller=c;
        return view;
    }
    private void CloseApp(){
        System.exit(0);
    }
    public void changeScene(Scenes scenes, Object data) throws IOException{
        View view = loadFXML(scenes);
        borderPane.setCenter(view.scene);
        this.centerController = view.controller;
        this.centerController.onOpen(data);
    }
    public void openModalv(Scenes scenes, String tilte, Controller parent, Object data) throws IOException {
        View view = loadFXML(scenes);
        Stage stage = new Stage();
        stage.setTitle(tilte);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.stage);
        Scene _scene = new Scene(view.scene);
        stage.setScene(_scene);
        view.controller.onOpen(data);
        stage.showAndWait();
    }
    public static void alertaResgis(){
        alerta.setContentText("Se ha producido un error,(cuenta ya registrada o has escrito mal los datos )");
        alerta.setWidth(500);
        alerta.setHeight(500);
        alerta.showAndWait();
    }
    public static void alerNotFound(){
        alerta.setContentText("Cuenta no encontrada");
        alerta.showAndWait();
    }
    public static void exerInvaliddata(){
        alerta.setContentText("Datos de ejercicios no validos");
        alerta.showAndWait();
    }
    public static void Numernegativo(){
        alerta.setContentText("no puede ser negativo");
        alerta.showAndWait();
    }
    public static void routineInvaliddata(){
        alerta.setContentText("Los datos de la rutinas son incorrectos");
        alerta.showAndWait();
    }
    public static void invaliddata(){
        alerta.setContentText("El nombre no es valido");
        alerta.showAndWait();
    }
    @FXML
    private void closeWindow(Event event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public static void Error(){
        alerta.setContentText("Ha ocurrido un error");
        alerta.showAndWait();
    }
    public static void exisMulti(){
        alerta.setContentText("Ya tiene Multimedia");
        alerta.showAndWait();
    }
    public static void notMultimedia(){
        alerta.setContentText("No tiene multimedia");
        alerta.showAndWait();
    }
}
