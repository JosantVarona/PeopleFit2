package dam.JosantVarona.test;

import dam.JosantVarona.Model.DAO.RutinaDAO;
import dam.JosantVarona.Model.Entity.Rutina;

public class TestSe {
    public static void main(String[] args) {
        Rutina r = RutinaDAO.build().findByid(15);
        System.out.println(r);
    }
}
