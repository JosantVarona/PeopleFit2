package dam.JosantVarona.View;

public enum Scenes {
    ROOT("View/layaut.fxml"),
    INICIO("View/INICIO.fxml"),
    LOGIN("View/login.fxml"),
    REGISTER("View/REGISTER.fxml"),
    Pricipal("View/Principal.fxml"),
    CrearRutina("View/CREARRUTI.fxml"),
    PERFIL("View/PERFIL.fxml"),
    MODIFICAR("View/Modify.fxml"),
    EJERCICIOS("View/crearEjer.fxml"),
    EDIT("View/EditRutina.fxml"),
    MOSTRAR("View/EjerciciosR.fxml"),
    AÑADIR("View/Agregar.fxml");

    private String url;
    Scenes(String url){
        this.url = url;
    }
    public String getUrl(){
        return url;
    }
}
