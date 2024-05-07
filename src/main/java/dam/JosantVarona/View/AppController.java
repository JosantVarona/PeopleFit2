package dam.JosantVarona.View;

import dam.JosantVarona.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

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
        changeScene(Scenes.INICIO,null);

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
    public static void alertaResgis(){
        alerta.setContentText("Se ha producido un error,(cuenta ya registrada o has escrito mal los datos )");
        alerta.showAndWait();
    }
    public static void alernoEncontrada(){
        alerta.setContentText("Cuenta no encontrada");
        alerta.showAndWait();
    }
}
