package dam.JosantVarona.View;

import dam.JosantVarona.Model.DAO.MultimediaDAO;
import dam.JosantVarona.Model.Entity.Exercise;
import dam.JosantVarona.Model.Entity.Multimedia;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ImgController extends Controller implements Initializable {
    @FXML
    private ImageView imageView;
    @FXML
    private Button delete;
    private Multimedia multimedia;
    /**
     * Handles the opening of the view and loads multimedia data associated with the provided Exercise input.
     *
     * @param input The Exercise object representing the exercise for which multimedia data needs to be loaded.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void onOpen(Object input) throws IOException {
        MultimediaDAO m = new MultimediaDAO();
        Exercise exercise = (Exercise) input;
        Integer find=exercise.getId();
        this.multimedia = m.findByid(find);
        byte[] data = multimedia.getData();
        if(data!=null){
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            Image image = new Image(bis);
            imageView.setImage(image);
        }
        //imageView.setImage();
    }

    @Override
    public void onClose(Object response) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Delete Photo the Exercise associated
     * @throws SQLException
     */
    @FXML
    private void deleteImg() throws SQLException {
        if (multimedia != null) {
            MultimediaDAO m = new MultimediaDAO();
            m.delete(multimedia);
        }

    }
    @FXML
    private void closeWindow(Event event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
