package dam.JosantVarona.Model.Entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Usuario {
    private Integer id;
    private String cuenta;
    private String pass;
    private String name;
    private List<Rutina> rutinas;

    public Usuario(Integer id, String cuenta, String pass, String name, List<Rutina> rutinas) {
        this.id = id;
        this.cuenta = cuenta;
        this.pass = pass;
        this.name = name;
        this.rutinas = rutinas;
    }
    public Usuario(String cuenta,String pass,String name){
        this.cuenta = cuenta;
        this.pass=pass;
        this.name = name;
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
        this.pass=pass;
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
    public static boolean validarCorreo(String gmail) {
        boolean result = false;
        Pattern gmailPattern = Pattern.compile("[A-Za-z0-9]+@+(gmail|hotmail)\\.(com|es)");
        Matcher gmailMatcher = gmailPattern.matcher(gmail);
        if (gmailMatcher.matches()) {
            result = true;
        }
        return result;
    }
    public static boolean validarnombre(String name){
        boolean result = false;
        Pattern namePattern = Pattern.compile("[A-Za-z0-9]+");
        Matcher nameMatcher = namePattern.matcher(name);
        if (nameMatcher.matches()) {
            result = true;
        }
        return result;
    }
    public static String segurity(String pass){
        String result = null;
        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashedBytes = digest.digest(pass.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            String hashedPassword = stringBuilder.toString();

            result = hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
}
