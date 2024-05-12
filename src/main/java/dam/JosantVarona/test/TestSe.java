package dam.JosantVarona.test;

import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.DAO.UsuarioDAO;
import dam.JosantVarona.Model.Entity.Rutina;
import dam.JosantVarona.Model.Entity.Usuario;

import java.util.List;

public class TestSe {
    public static void main(String[] args) {
        UsuarioDAO us = new UsuarioDAO();
        Usuario u = us.Identificardor(3);
        List<Rutina> r = RutinaDAO.build().findByUsuario(u);
        //Rutina r = RutinaDAO.build().findByid(20);
        System.out.println(r);

    }
}
