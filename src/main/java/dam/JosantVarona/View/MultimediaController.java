package dam.JosantVarona.View;

import dam.JosantVarona.Model.DAO.MultimediaDAO;
import dam.JosantVarona.Model.Entity.Exercise;
import dam.JosantVarona.Model.Entity.Multimedia;
import dam.JosantVarona.Model.Entity.Photos;
import dam.JosantVarona.Model.Entity.Video;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MultimediaController extends Controller implements Initializable {

    private Exercise exerciseM =null;
    @FXML
    private ImageView imageView;
    @FXML
    private Button selectPhoto;
    @FXML
    private TextField textPhoto;

    @FXML
    private TextField textVideo;
    @FXML
    private Button selecVideo;
    @FXML
    private Label URL;
    private File video;
    private File view;
    private String extension;

    /**
     *
     * @param input receive an exercise
     * @throws IOException
     */
    @Override
    public void onOpen(Object input) throws IOException {
        Exercise exercise = (Exercise) input;
        exerciseM = exercise;
    }

    @Override
    public void onClose(Object response) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /**
     * Converts an image file to a byte array.
     *
     * @param file The image file to be converted.
     * @return The byte array representation of the image.
     * @throws IOException If an I/O error occurs.
     */
    public static byte[] imagenToBytes(File file) throws IOException{
        FileInputStream fis = new FileInputStream(file);

        byte[] bytes = new byte[(int) file.length()];
        fis.read(bytes);
        fis.close();
        return bytes;
    }
    /**
     * Opens a file chooser dialog to select an image file and displays the selected image in an ImageView.
     */
    @FXML
    private void chooseView(){
        FileChooser file = new FileChooser();
        file.setTitle("Seleciona el archivo");
        file.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.jpeg","*.gif")
        );
        File select = file.showOpenDialog(selectPhoto.getScene().getWindow());
        if (select != null){
            view = select;

            Image image = new Image(select.toURI().toString());
            imageView.setImage(image);
        }
    }
    /**
     * Opens a file chooser dialog to select a video file and sets the selected video file's name to a Text field.
     */
    @FXML
    private void chooseVideo(){
        FileChooser file = new FileChooser();
        file.setTitle("Seleccione un video");
        file.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Video Files","*.mp4","*.avi","*.mov")
        );
        File select = file.showOpenDialog(selecVideo.getScene().getWindow());
        if (select != null){
            video = select;
            URL.setText(select.getName());
        }
    }

    /**
     * Collect Data the Photo
     * @return photo
     * @throws IOException
     */
    private Photos dataPhoto() throws IOException {
        Photos result = null;
        File file = view;
        if (file !=null) {
            byte[] imagen = imagenToBytes(file);
            String name = textPhoto.getText();
            String extension = "mutimedia";
            if (!name.isEmpty()) {
                Photos aux = new Photos(name, extension, exerciseM, imagen);
                result = aux;
            }
        }
        return result;
    }

    /**
     * Collect Data the Video
     * @return Video
     * @throws IOException
     */
    private Video datavideo() throws  IOException{
        Video result = null;
        File file = video;
        if (file != null) {
            byte[] video = imagenToBytes(file);
            String name = textVideo.getText();
            String extension = "multimedia";
            if (!name.isEmpty()) {
                Video aux = new Video(name, extension, exerciseM, video);
                result = aux;
            }
        }
        return result;
    }

    /**
     * Save Photo database
     * @throws IOException
     */
    @FXML
    private void saveMultimedia() throws IOException {
        MultimediaDAO mul = new MultimediaDAO();
        Photos photos = dataPhoto();
        if (photos != null){
            mul.save(photos);
        }else{
            AppController.Error();
        }
    }

    /**
     * save Video database
     * @throws IOException
     */
    @FXML
    private void saveVideo() throws IOException {
        MultimediaDAO mul = new MultimediaDAO();
        Video video = datavideo();
        if (video != null){
            mul.save(video);
        }else{
            AppController.Error();
        }
    }
    @FXML
    private void closeWindow(Event event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
