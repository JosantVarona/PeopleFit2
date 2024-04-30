package dam.JosantVarona.test;

import dam.JosantVarona.Model.DAO.EjercicioDAO;
import dam.JosantVarona.Model.Entity.Ejercicio;

public class insert1 {
    public static void main(String[] args) {
        EjercicioDAO ejDAO =new EjercicioDAO();
        Ejercicio ej = new Ejercicio(3,10,"press militar");
        ejDAO.save(ej);
    }
}
