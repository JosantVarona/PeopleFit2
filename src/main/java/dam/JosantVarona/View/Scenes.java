package dam.JosantVarona.View;

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
    DELETEUSER("View/Eliminar.fxml");

    private String url;
    Scenes(String url){
        this.url = url;
    }
    public String getUrl(){
        return url;
    }
}
