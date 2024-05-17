package dam.JosantVarona.test;

import dam.JosantVarona.Model.DAO.UsuarioDAO;
import dam.JosantVarona.Model.Entity.User;

public class inserUser {
    public static void main(String[] args) {
        User u = new User("prueba","12345","prueba");
        UsuarioDAO us = new UsuarioDAO();
        us.save(u);
    }
}
