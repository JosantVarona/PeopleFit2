package dam.JosantVarona.Model.Entity;

public class UserSesion {
    private static UserSesion _instance;
    private static Usuario userLoged;

    private UserSesion() {
    }

    public static UserSesion getInstancia() {
        if (_instance == null) {
            _instance = new UserSesion();
            _instance.logIn(userLoged);
        }
        return _instance;
    }

    public void logIn(Usuario user) {
        userLoged = user;
    }

    public Usuario getUsuarioIniciado() {
        return userLoged;
    }

    public void logOut() {
        userLoged = null;
    }
}
