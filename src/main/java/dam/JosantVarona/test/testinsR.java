package dam.JosantVarona.test;

import dam.JosantVarona.Model.DAO.EjercicioDAO;
import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.DAO.UsuarioDAO;
import dam.JosantVarona.Model.Entity.Exercise;
import dam.JosantVarona.Model.Entity.Routine;

public class testinsR {
    public static void main(String[] args) {
        Routine r= RutinaDAO.build().findByid(15);

        EjercicioDAO ej = new EjercicioDAO();
        Exercise e = ej.findByid(4);
        r.addEjercicio(e);

        UsuarioDAO us = new UsuarioDAO();
        /*User u = us.findByid(1);
        r.setUsuario(u);
        u.addRutina(r);
        System.out.println(u);
        /*us.save(u);*/
    }
}
