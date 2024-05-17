package dam.JosantVarona.test;

import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.DAO.UsuarioDAO;
import dam.JosantVarona.Model.Entity.Routine;
import dam.JosantVarona.Model.Entity.User;

import java.util.List;

public class TestSe {
    public static void main(String[] args) {
        UsuarioDAO us = new UsuarioDAO();
        User u = us.Identify(3);
        List<Routine> r = RutinaDAO.build().findByUsuario(u);
        //Routine r = RutinaDAO.build().findByid(20);
        System.out.println(r);

    }
}
