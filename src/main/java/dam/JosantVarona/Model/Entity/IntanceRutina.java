package dam.JosantVarona.Model.Entity;

import dam.JosantVarona.Model.DAO.RutinaDAO;

public class IntanceRutina {
    private static IntanceRutina _instance;
    private static Rutina rutinaLogin;

    private IntanceRutina() {
    }

    public static IntanceRutina getInstancia() {
        if (_instance == null) {
            _instance = new IntanceRutina();
            _instance.logR(rutinaLogin);
        }
        return _instance;
    }

    public void logR(Rutina rutina) {

        rutinaLogin = rutina;
    }

    public Rutina getRutinaLogin() {
        return rutinaLogin;
    }

    public void logOut() {
        rutinaLogin = null;
    }
}

