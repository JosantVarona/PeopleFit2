package dam.JosantVarona.Model.Entity;

public class Photos extends Multimedia{

    public Photos(String name,String extension,Exercise exercise,byte[] data) {
        super(name,extension,"Foto",exercise,data);
    }

}
