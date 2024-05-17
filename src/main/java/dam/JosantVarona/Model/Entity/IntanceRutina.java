package dam.JosantVarona.Model.Entity;

public class IntanceRutina {
    private static IntanceRutina _instance;
    private static Routine rutinaLogin;

    private IntanceRutina() {
    }

    public static IntanceRutina getInstancia() {
        if (_instance == null) {
            _instance = new IntanceRutina();
            _instance.logR(rutinaLogin);
        }
        return _instance;
    }

    public void logR(Routine rutina) {

        rutinaLogin = rutina;
    }

    public Routine getRutinaLogin() {
        return rutinaLogin;
    }

    public void logOut() {
        rutinaLogin = null;
    }
}

