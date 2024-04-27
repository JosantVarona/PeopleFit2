package dam.JosantVarona.test;

import dam.JosantVarona.Model.DAO.EjercicioDAO;
import dam.JosantVarona.Model.Entity.Ejercicio;

public class insert1 {
    public static void main(String[] args) {
        Ejercicio e = new Ejercicio(1,3,10,"Bizeps");
        EjercicioDAO eDAO =new EjercicioDAO();
        eDAO.save(e);
    }
}
