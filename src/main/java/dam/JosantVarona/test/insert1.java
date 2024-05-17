package dam.JosantVarona.test;

import dam.JosantVarona.Model.DAO.EjercicioDAO;
import dam.JosantVarona.Model.Entity.Exercise;

public class insert1 {
    public static void main(String[] args) {
        EjercicioDAO ejDAO =new EjercicioDAO();
        Exercise ej = new Exercise(3,10,"press militar");
        ejDAO.save(ej);
    }
}
