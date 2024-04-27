package dam.JosantVarona.Model.DAO;

import dam.JosantVarona.Model.Connection.ConnectionMariaDB;
import dam.JosantVarona.Model.Entity.Ejercicio;
import dam.JosantVarona.Model.Entity.Rutina;
import dam.JosantVarona.Model.Entity.Usuario;
import dam.JosantVarona.Model.Enum.Dia;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RutinaDAO implements DAO<Rutina,Integer> {
    private static final String FINDBYID = "SELECT r.id,r.Dia,r.Descripcion,r.Fecha,ID_usuario FROM rutina AS r WHERE r.id=?";
    private static final String INSERT = "INSERT INTO rutina (Dia,Descripcion,Fecha,ID_usuario) VALUES (?,?,?,?)";
    private static final String FINDALL = "SELECT * FROM ejercicio";
    private static final String UPDATE = "UPDATE rutina SET Dia=? WHERE id=?";
    private static final String DELETE = "DELETE FROM rutina WHERE id=?";
    private static final String INSERTEJ = "INSERT INTO pertenece (ID_rutina,ID_ejercicio) VALUES(?,?)";
    private static final String DELETEALLEJ = "DELETE FROM pertenece WHERE ID_rutina=?";
    /*private static final String CREATEID = "SELECT r.id FROM rutina";*/
    private static final String FINDEJER ="SELECT r.Descripcion, e.* FROM rutina r, ejercicio e, pertenece p WHERE r.id = p.ID_rutina AND e.id = p.ID_ejercicio";


    private Connection conn;

    public RutinaDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Rutina save(Rutina entity) {
        Rutina result = entity;
        if (entity==null) return result;
        if (entity.getId()==null) {
                    try (PreparedStatement pst = conn.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS)) {
                        //insert
                        pst.setString(1, (entity.getDia()).name());
                        pst.setString(2, entity.getDescripcion());
                        pst.setDate(3, Date.valueOf(entity.getFecha()));
                        //id usuario
                        pst.setInt(4, entity.getUsuario().getId());

                        pst.executeUpdate(); //insertada
                        try (ResultSet resultSet = pst.getGeneratedKeys()) {
                            if (resultSet.first()) {
                                entity.setId(resultSet.getInt(1));
                                //Ahora procedo a asociar los ejercicios con esta rutina
                                asociaEjercicios(entity);
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    findByid(entity.getId());
                    //UPDATE
                    //Actualizar los datos propios de la rutina
                    try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    //por hacer aun
                    //Actualizar los ejercicios
                    asociaEjercicios(entity);
                }

        return result;
    }
    private void asociaEjercicios(Rutina entity){
        //borrando todos los que tuviera antes
        try (PreparedStatement pst = conn.prepareStatement(DELETEALLEJ)){
            pst.setInt(1,entity.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Ahora inserto los ejercicios
        if (entity.getEjercicios()!=null) {
            for(Ejercicio e:entity.getEjercicios()){
                try (PreparedStatement pst = conn.prepareStatement(INSERTEJ)) {
                    pst.setInt(1,entity.getId());
                    pst.setInt(2,e.getId());
                    pst.executeUpdate();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    @Override
    public Rutina delete(Rutina entity) throws SQLException {
        if (entity !=null){
            try (PreparedStatement pst = conn.prepareStatement(DELETE)){
                pst.setInt(1,entity.getId());
                pst.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
                entity = null;
            }
        }
        return entity;
    }

    @Override
    public Rutina findByid(Integer primaria) {
        Rutina result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, primaria);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Rutina r = new Rutina();
                    r.setId(res.getInt("id"));
                    r.setDia(Dia.valueOf(res.getString("Dia")));
                    r.setDescripcion(res.getString("Descripcion"));
                    //ejercicios
                    r.setFecha(res.getDate("Fecha").toLocalDate());
                    //Usuario

                    result = r;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void close() throws IOException {

    }
    /*private List<Integer> Createid(){
        List<Integer> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(CREATEID)) {

            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Rutina r = new Rutina();
                Integer i;
                r.setId(res.getInt("id"));
                i= r.getId();

                result.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }*/

    public List<Ejercicio> FindAllEjer() {
        List<Ejercicio> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {

            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Ejercicio e = new Ejercicio();
                e.setName(res.getString("name"));
                e.setSerie(res.getInt("serie"));
                e.setRepes(res.getInt("repes"));

                result.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static RutinaDAO build(){
        return new RutinaDAO();
    }
}