package dam.JosantVarona.Model.DAO;

import dam.JosantVarona.Model.Connection.ConnectionMariaDB;
import dam.JosantVarona.Model.Entity.Exercise;
import dam.JosantVarona.Model.Entity.Routine;
import dam.JosantVarona.Model.Entity.User;
import dam.JosantVarona.Model.Enum.Dia;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RutinaDAO implements DAO<Routine,Integer> {
    private static final String FINDBYID = "SELECT r.id,r.Dia,r.Descripcion,r.Fecha,ID_usuario FROM rutina AS r WHERE r.id=?";
    private static final String INSERT = "INSERT INTO rutina (Dia,Descripcion,Fecha,ID_usuario) VALUES (?,?,?,?)";
    private static final String FINDALL = "SELECT * FROM ejercicio";
    private static final String UPDATE = "UPDATE rutina SET Dia=?,Descripcion=? WHERE id=?";
    private static final String DELETE = "DELETE FROM rutina WHERE id=?";
    private static final String INSERTEJ = "INSERT INTO pertenece (ID_rutina,ID_ejercicio) VALUES(?,?)";
    private static final String DELETEALLEJ = "DELETE FROM pertenece WHERE ID_rutina=?";
    private static final String FINDBYUSUARIO = "SELECT r.id,r.Dia,r.Descripcion,r.Fecha,ID_usuario FROM rutina AS r WHERE r.id_usuario=?";
    private static final String FINDEJER ="SELECT e.* FROM rutina r, ejercicio e, pertenece p WHERE r.id = p.ID_rutina AND e.id = p.ID_ejercicio AND r.id=?";


    private Connection conn;

    public RutinaDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    /**
     * Saves a Routine entity to the database.
     *
     * @param entity The Routine entity to be saved.
     * @return The saved Routine entity.
     */
    public Routine save(Routine entity) {
        Routine result = entity;
        if (entity==null) return result;
        if (entity.getId()==null || entity.getId()==0) {
                    try (PreparedStatement pst = conn.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS)) {
                        //insert
                        pst.setString(1, (entity.getDay()).name());
                        pst.setString(2, entity.getName());
                        pst.setDate(3, Date.valueOf(entity.getDateR()));
                        //id usuario
                        pst.setInt(4, entity.getUser().getId());

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
                        pst.setString(1,entity.getDay().name());
                        pst.setString(2, entity.getName());
                        pst.setInt(3,entity.getId());
                        pst.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    //Actualizar los ejercicios
            asociaEjercicios(entity);
                }

        return result;
    }
    /**
     * Associates exercises with a Routine entity.
     *
     * @param entity The Routine entity to associate exercises with.
     */
    public void asociaEjercicios(Routine entity){
        //borrando todos los que tuviera antes
        try (PreparedStatement pst = conn.prepareStatement(DELETEALLEJ)){
            pst.setInt(1,entity.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Ahora inserto los ejercicios
        if (entity.getExercises()!=null) {
            for(Exercise e:entity.getExercises()){
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
    /**
     * Deletes a Routine entity from the database.
     *
     * @param entity The Routine entity to be deleted.
     * @return The deleted Routine entity, or null if deletion fails.
     * @throws SQLException If an SQL error occurs during deletion.
     */
    public Routine delete(Routine entity) throws SQLException {
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
    /**
     * Finds a Routine entity by its ID.
     *
     * @param primaria The ID of the Routine entity to find.
     * @return The Routine entity with the specified ID, or null if not found.
     */
    public Routine findByid(Integer primaria) {
        Routine result = null;
        UsuarioDAO usDAO = new UsuarioDAO();
        try (PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, primaria);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Routine r = new Routine();
                    r.setId(res.getInt("id"));
                    r.setDay(Dia.valueOf(res.getString("Dia").toUpperCase()));
                    r.setName(res.getString("Descripcion"));
                    //ejercicios
                    r.setDateR(res.getDate("Fecha").toLocalDate());
                    r.setUser(usDAO.Identify(res.getInt("ID_usuario")));
                    //User

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
    /**
     * Retrieves all Exercise entities from the database.
     *
     * @return A list of all Exercise entities.
     */
    public List<Exercise> FindAllEjer() {
        List<Exercise> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {

            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Exercise e = new Exercise();
                e.setId(res.getInt("id"));
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
    /**
     * Finds all routines associated with a specific user.
     *
     * @param u The user for which to find routines.
     * @return A list of Routine objects associated with the specified user.
     */
    public List<Routine> findByUsuario (User u){
        List<Routine> result = new ArrayList<>();
        if(u!=null || u.getId()!=null){
            try(PreparedStatement pst = conn.prepareStatement(FINDBYUSUARIO)){
                pst.setInt(1,u.getId());
                try(ResultSet res = pst.executeQuery()){
                    while (res.next()){
                        Routine r = new Routine();
                        r.setId(res.getInt("id"));
                        r.setDay(Dia.valueOf(res.getString("Dia").toUpperCase()));
                        r.setName(res.getString("Descripcion"));
                        r.setDateR(res.getDate("Fecha").toLocalDate());
                        r.setUser(u);
                        result.add(r);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * Finds all exercises associated with a specific routine.
     *
     * @param rutina The routine for which to find exercises.
     * @return A list of Exercise objects associated with the specified routine.
     */
    public List<Exercise> findEjercicios (Routine rutina){
        List<Exercise> result = new ArrayList<>();
        if (rutina != null){
            try(PreparedStatement pst = conn.prepareStatement(FINDEJER)){
                pst.setInt(1,rutina.getId());
                try(ResultSet res = pst.executeQuery()){
                    while (res.next()){
                        Exercise e = new Exercise();
                        e.setId(res.getInt("id"));
                        e.setName(res.getString("name"));
                        e.setSerie(res.getInt("serie"));
                        e.setRepes(res.getInt("repes"));
                        result.add(e);
                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return result;
    }
    /** Builds an instance of RutinaDAO.
     *
     * @return An instance of RutinaDAO.
     */
    public static RutinaDAO build(){
        return new RutinaDAO();
    }
}