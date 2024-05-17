package dam.JosantVarona.Model.Entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private Integer id;
    private String account;
    private String pass;
    private String name;
    private List<Routine> routines;

    public User(Integer id, String cuenta, String pass, String name, List<Routine> rutinas) {
        this.id = id;
        this.account = cuenta;
        this.pass = pass;
        this.name = name;
        this.routines = rutinas;
    }
    public User(String cuenta, String pass, String name){
        this.account = cuenta;
        this.pass=pass;
        this.name = name;
    }

    public User(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public List<Routine> getRoutines() {
        return routines;
    }

    public void setRoutines(List<Routine> routines) {
        this.routines = routines;
    }
    public void  addRutina(Routine rutina){
        if(routines == null) routines = new ArrayList<>();
        if(!routines.contains(rutina)) routines.add(rutina);
    }
    public void removeRutinas(Routine rutina){
        if(routines !=null){
            routines.remove(rutina);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User usuario = (User) o;
        return id == usuario.id && Objects.equals(account, usuario.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", cuenta='" + account + '\'' +
                ", pass='" + pass + '\'' +
                ", name='" + name + '\'' +
                ", rutinas=" + routines;
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
