package dam.JosantVarona.Model.Entity;

import dam.JosantVarona.Model.Enum.Dia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static dam.JosantVarona.Model.Enum.Dia.NINGUNO;

public class Routine {
    private Integer id;
    private Dia day;
    private String name;
    private LocalDate dateR;
    private List<Exercise> exercises;
    private User user;
    private Boolean deleteR;

    public Routine(Integer id, Dia dia, String descripcion, List<Exercise> ejercicios, User usuario) {
        this.id = id;
        this.day = dia;
        this.name = descripcion;
        this.exercises = ejercicios;
        this.user =usuario;
    }
    public Routine(Dia dia, String descripcion, List<Exercise> ejercicios, User usuario) {
        this.day = dia;
        this.name = descripcion;
        setName(descripcion);
        this.exercises = ejercicios;
        this.user = usuario;
    }
    public Routine(){
    this(0,NINGUNO,null,null,null);
    this.deleteR = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Dia getDay() {
        return day;
    }

    public void setDay(Dia day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if(name != null) dateR = LocalDate.now();
    }

    public LocalDate getDateR() {
        return dateR;
    }

    public void setDateR(LocalDate dateR) {
        dateR = LocalDate.now();
        this.dateR = dateR;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
    public void addEjercicio(Exercise ejercicio){
        if (exercises == null) exercises = new ArrayList<>();
        if (!exercises.contains(ejercicio)) exercises.add(ejercicio);
    }
    public void removerEjercicio(Exercise ejercicio){
        if (exercises !=null) exercises.remove(ejercicio);
    }
    public Exercise getEjericico(Exercise ejercicio){
        Exercise result=null;
        if (exercises !=null){
            int i = exercises.indexOf(ejercicio);
            result = exercises.get(i);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Routine rutina = (Routine) o;
        return id == rutina.id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getDeleteR() {
        return deleteR;
    }

    public void setDeleteR(Boolean deleteR) {
        this.deleteR = deleteR;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Routine{" +
                "id=" + id +
                ", dia=" + day +
                ", descripcion='" + name + '\'' +
                ", fecha=" + dateR +
                ", usuario=" + user.getId() +
                ", ejercicios=" + exercises +
                '}';
    }
}
