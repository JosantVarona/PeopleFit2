package dam.JosantVarona.Model.Entity;

import dam.JosantVarona.Model.Enum.Dia;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Rutina {
    private int id;
    private Dia dia;
    private String descripcion;
    private LocalDate fecha;
    private List<Ejercicio> ejercicios;

    public Rutina(int id, Dia dia, String descripcion, LocalDate fecha, List<Ejercicio> ejercicios) {
        this.id = id;
        this.dia = dia;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.ejercicios = ejercicios;
    }
    public Rutina(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rutina rutina = (Rutina) o;
        return id == rutina.id;
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
                ", ejercicios=" + ejercicios +
                '}';
    }
}
