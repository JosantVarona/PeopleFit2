package dam.JosantVarona.Model.Entity;

import java.util.Objects;

public class Multimedia {
    private Integer id;
    private String name;
    private String extension;
    private String type;
    private Exercise exercise;
    private byte[] data;


    public Multimedia(Integer id, String name, String extension, String type, Exercise exercise,byte[] data) {
        this.id = id;
        this.name = name;
        this.extension = extension;
        this.type = type;
        this.exercise = exercise;
        this.data = data;
    }
    public Multimedia(String name, String extension, String type, Exercise exercise,byte[] data) {
        this.name = name;
        this.extension = extension;
        this.type = type;
        this.exercise = exercise;
        this.data = data;
    }
    public Multimedia(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Multimedia that = (Multimedia) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
