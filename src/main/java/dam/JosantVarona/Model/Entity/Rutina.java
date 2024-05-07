package dam.JosantVarona.Model.Entity;

import dam.JosantVarona.Model.Enum.Dia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Rutina {
    private Integer id;
    private Dia dia;
    private String descripcion;
    private LocalDate fecha;
    private List<Ejercicio> ejercicios;
    private Usuario usuario;

    public Rutina(Integer id, Dia dia, String descripcion,List<Ejercicio> ejercicios,Usuario usuario) {
        this.id = id;
        this.dia = dia;
        this.descripcion = descripcion;
        this.ejercicios = ejercicios;
        this.usuario=usuario;
    }
    public Rutina( Dia dia, String descripcion, List<Ejercicio> ejercicios,Usuario usuario) {
        this.dia = dia;
        this.descripcion = descripcion;
        setDescripcion(descripcion);
        this.ejercicios = ejercicios;
        this.usuario = usuario;
    }
    public Rutina(){
    this(0,null,null,null,null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        if(descripcion != null) fecha = LocalDate.now();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        fecha = LocalDate.now();
        this.fecha = fecha;
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }
    public void addEjercicio(Ejercicio ejercicio){
        if (ejercicios == null) ejercicios = new ArrayList<>();
        if (!ejercicios.contains(ejercicio)) ejercicios.add(ejercicio);
    }
    public void removerEjercicio(Ejercicio ejercicio){
        if (ejercicios!=null) ejercicios.remove(ejercicio);
    }
    public Ejercicio getEjericico(Ejercicio ejercicio){
        Ejercicio result=null;
        if (ejercicios!=null){
            int i =ejercicios.indexOf(ejercicio);
            result = ejercicios.get(i);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rutina rutina = (Rutina) o;
        return id == rutina.id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Rutina{" +
                "id=" + id +
                ", dia=" + dia +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", usuario=" + usuario.getName() +
                ", ejercicios=" + ejercicios +
                '}';
    }
}
