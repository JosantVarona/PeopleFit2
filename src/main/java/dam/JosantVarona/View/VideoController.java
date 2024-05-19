package dam.JosantVarona.View;


import dam.JosantVarona.Model.Entity.Multimedia;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.media.MediaView;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VideoController extends Controller implements Initializable {
    private Multimedia multimedia;
    @Override
    public void onOpen(Object input) throws IOException {
        /*MultimediaDAO m = new MultimediaDAO();
        Exercise exercise = (Exercise) input;
        Integer find=exercise.getId();
        this.multimedia = m.findByid(find);
        byte[] data = multimedia.getData();
        if(data!=null) {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            Media me = new Media();
        }*/
    }

    @Override
    public void onClose(Object response) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
