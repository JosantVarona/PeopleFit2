package dam.JosantVarona.Model.Entity;

import java.util.List;
import java.util.Objects;

public class Usuario {
    private int id;
    private String cuenta;
    private String pass;
    private String name;
    private List<Rutina> rutinas;

    public Usuario(int id, String cuenta, String pass, String name, List<Rutina> rutinas) {
        this.id = id;
        this.cuenta = cuenta;
        this.pass = pass;
        this.name = name;
        this.rutinas = rutinas;
    }

    public Usuario(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Rutina> getRutinas() {
        return rutinas;
    }

    public void setRutinas(List<Rutina> rutinas) {
        this.rutinas = rutinas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id && Objects.equals(cuenta, usuario.cuenta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cuenta);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", cuenta='" + cuenta + '\'' +
                ", pass='" + pass + '\'' +
                ", name='" + name + '\'' +
                ", rutinas=" + rutinas +
                '}';
    }
}
