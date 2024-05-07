package dam.JosantVarona.test;

import dam.JosantVarona.Model.DAO.UsuarioDAO;
import dam.JosantVarona.Model.Entity.Usuario;

public class inserUser {
    public static void main(String[] args) {
        Usuario u = new Usuario("prueba","12345","prueba");
        UsuarioDAO us = new UsuarioDAO();
        us.save(u);
    }
}
