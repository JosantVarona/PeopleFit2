package dam.JosantVarona.Model.Entity;

import java.util.Objects;

public class Exercise {
    private Integer id;
    private Integer serie;
    private Integer repes;
    private String name;
    private Boolean add;
    public Exercise(Integer id, Integer serie, Integer repes, String name) {
        this.id = id;
        this.serie = serie;
        this.repes = repes;
        this.name = name;
        this.add = false;
    }
    public Exercise(Integer serie, Integer repes, String name) {
        this.serie = serie;
        this.repes = repes;
        this.name = name;
        this.add = false;
    }
    public Exercise(){
        this.add = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSerie() {
        return serie;
    }

    public void setSerie(Integer serie) {
        this.serie = serie;
    }

    public Integer getRepes() {
        return repes;
    }

    public void setRepes(Integer repes) {
        this.repes = repes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAdd() {
        return add;
    }

    public void setAdd(Boolean add) {
        this.add = add;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise ejercicio = (Exercise) o;
        return id == ejercicio.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", serie=" + serie +
                ", repes=" + repes +
                ", name='" + name + '\'' +
                '}';
    }
}
