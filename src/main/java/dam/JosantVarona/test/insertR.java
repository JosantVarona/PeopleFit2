package dam.JosantVarona.test;

import dam.JosantVarona.Model.DAO.EjercicioDAO;
import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.DAO.UsuarioDAO;
import dam.JosantVarona.Model.Entity.Ejercicio;
import dam.JosantVarona.Model.Entity.Rutina;
import dam.JosantVarona.Model.Entity.Usuario;
import dam.JosantVarona.Model.Enum.Dia;

import java.util.ArrayList;

public class insertR {
    public static void main(String[] args) {
        UsuarioDAO us=new UsuarioDAO();
        RutinaDAO r = new RutinaDAO();
        Usuario u = us.findByid(1);
        Rutina rutina = new Rutina(Dia.Lunes,"Brazo", new ArrayList<>(),u);
        EjercicioDAO e= new EjercicioDAO();
        Ejercicio ej = e.findByid(1);
        rutina.addEjercicio(ej);
        r.save(rutina);
        System.out.println(u);

    }
}
