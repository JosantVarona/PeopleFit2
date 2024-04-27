package dam.JosantVarona.test;

import dam.JosantVarona.Model.DAO.EjercicioDAO;
import dam.JosantVarona.Model.DAO.UsuarioDAO;
import dam.JosantVarona.Model.Entity.Ejercicio;
import dam.JosantVarona.Model.Entity.Usuario;

public class testSelect {
    public static void main(String[] args) {
        EjercicioDAO eDAo = new EjercicioDAO();
        Ejercicio e = eDAo.findByid(1);
        System.out.println(e);
        UsuarioDAO uDAO= new UsuarioDAO();
        System.out.println(uDAO.findByid(1));

    }
}