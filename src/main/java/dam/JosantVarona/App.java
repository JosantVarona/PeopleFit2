package dam.JosantVarona;

import dam.JosantVarona.View.AppController;
import dam.JosantVarona.View.Scenes;
import dam.JosantVarona.View.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    public static Stage stage;
    public static AppController currentController;

    @Override
    /**
     * Load the root FXML scene
     * Create a new Scene with the loaded FXML scene and set the dimensions
     * Retrieve the controller associated with the loaded FXML scene
     * Call the onOpen method of the controller with null data (if needed)
     * Set the scene for the main stage
     * Show the main stage
     */
    public void start(Stage stage) throws IOException {
        View view = AppController.loadFXML(Scenes.ROOT);
        scene = new Scene(view.scene,1600,900);
        currentController = (AppController) view.controller;
        currentController.onOpen(null);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * launch program
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}