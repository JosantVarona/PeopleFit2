package dam.JosantVarona.test;

import dam.JosantVarona.Model.DAO.EjercicioDAO;
import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.DAO.UsuarioDAO;
import dam.JosantVarona.Model.Entity.Ejercicio;
import dam.JosantVarona.Model.Entity.Rutina;
import dam.JosantVarona.Model.Entity.Usuario;

public class testinsR {
    public static void main(String[] args) {
        Rutina r= RutinaDAO.build().findByid(15);

        EjercicioDAO ej = new EjercicioDAO();
        Ejercicio e = ej.findByid(4);
        r.addEjercicio(e);

        UsuarioDAO us = new UsuarioDAO();
        /*Usuario u = us.findByid(1);
        r.setUsuario(u);
        u.addRutina(r);
        System.out.println(u);
        /*us.save(u);*/
    }
}
