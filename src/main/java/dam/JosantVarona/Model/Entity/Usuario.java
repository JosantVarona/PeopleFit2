package dam.JosantVarona.Model.Entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
    private Integer id;
    private String cuenta;
    private String pass;
    private String name;
    private List<Rutina> rutinas;

    public Usuario(Integer id, String cuenta, String pass, String name, List<Rutina> rutinas) {
        this.id = id;
        this.cuenta = cuenta;
        setPass(pass);
        this.name = name;
        this.rutinas = rutinas;
    }

    public Usuario(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    public void  addRutina(Rutina rutina){
        if(rutinas == null) rutinas = new ArrayList<>();
        if(!rutinas.contains(rutina))rutinas.add(rutina);
    }
    public void removeRutinas(Rutina rutina){
        if(rutinas!=null){
            rutinas.remove(rutina);
        }
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
                ", rutinas=" + rutinas;
    }
    /*public boolean comparePassword(String inputPassword) {
        boolean comp;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashedBytes = digest.digest(inputPassword.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            String hashedInputPassword = stringBuilder.toString();

            comp = hashedInputPassword.equals(pass);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            comp = false;
        }
        return comp;
    }*/


}
