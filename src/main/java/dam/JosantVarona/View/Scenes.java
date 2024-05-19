package dam.JosantVarona.View;

import dam.JosantVarona.Model.Entity.Photos;

public enum Scenes {
    ROOT("View/layaut.fxml"),
    START("View/INICIO.fxml"),
    LOGIN("View/login.fxml"),
    REGISTER("View/REGISTER.fxml"),
    BEGINNING("View/Principal.fxml"),
    CREATEROUTINE("View/CREARRUTI.fxml"),
    PROFILE("View/PERFIL.fxml"),
    MODIFICAR("View/Modify.fxml"),
    EXERCISE("View/crearEjer.fxml"),
    EDIT("View/EditRutina.fxml"),
    SHOW("View/EjerciciosR.fxml"),
    ADD("View/Agregar.fxml"),
    ROUTINE("View/RutinasP.fxml"),
    DELETEUSER("View/Eliminar.fxml"),
    MULTIMEDIA("View/AddMultimedia.fxml"),
    SHOWMULTI("View/Imagen.fxml"),
    SHOWVIDEO("View/video.fxml");

    private String url;
    Scenes(String url){
        this.url = url;
    }
    public String getUrl(){
        return url;
    }
}
