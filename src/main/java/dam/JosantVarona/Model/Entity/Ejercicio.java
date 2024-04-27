package dam.JosantVarona.Model.Entity;

import java.util.Objects;

public class Ejercicio {
    private Integer id;
    private int serie;
    private int repes;
    private String name;

    public Ejercicio(Integer id, int serie, int repes, String name) {
        this.id = id;
        this.serie = serie;
        this.repes = repes;
        this.name = name;
    }
    public Ejercicio( Integer serie, int repes, String name) {
        this.serie = serie;
        this.repes = repes;
        this.name = name;
    }
    public Ejercicio(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public int getRepes() {
        return repes;
    }

    public void setRepes(int repes) {
        this.repes = repes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ejercicio ejercicio = (Ejercicio) o;
        return id == ejercicio.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ejercicio{" +
                "id=" + id +
                ", serie=" + serie +
                ", repes=" + repes +
                ", name='" + name + '\'' +
                '}';
    }
}
