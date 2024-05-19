package dam.JosantVarona.Model.Entity;

public class Video extends Multimedia{

    public Video( String name, String extension, Exercise exercise, byte[] data) {
        super( name, extension, "Video", exercise, data);
    }
}
