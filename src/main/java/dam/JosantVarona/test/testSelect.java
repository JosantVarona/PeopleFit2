package dam.JosantVarona.test;

import dam.JosantVarona.Model.DAO.EjercicioDAO;
import dam.JosantVarona.Model.DAO.UsuarioDAO;
import dam.JosantVarona.Model.Entity.Exercise;

public class testSelect {
    public static void main(String[] args) {
        EjercicioDAO eDAo = new EjercicioDAO();
        Exercise e = eDAo.findByid(1);
        System.out.println(e);
        UsuarioDAO uDAO= new UsuarioDAO();


    }
}