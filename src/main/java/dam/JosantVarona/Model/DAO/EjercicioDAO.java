package dam.JosantVarona.Model.DAO;

import dam.JosantVarona.Model.Connection.ConnectionMariaDB;
import dam.JosantVarona.Model.Entity.Exercise;
import dam.JosantVarona.Model.Entity.Routine;
import dam.JosantVarona.Model.Enum.Dia;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EjercicioDAO implements DAO<Exercise, Integer>{
    private static final String INSERT = "INSERT INTO ejercicio(serie,repes,name) VALUES (?,?,?)";
    private static final String FINDID = "SELECT e.id,e.name,e.serie,e.repes FROM ejercicio AS e WHERE e.id=?";
    private static final String UPDATE = "UPDATE ejercicio SET serie=?,repes=?,name=? WHERE id=?";
    private static final String DELETE = "DELETE FROM ejercicio WHERE id=?";
    private static final String BYRUTINA = "SELECT r.id,r.Descripcion,r.ID_usuario,r.Dia FROM rutina r, ejercicio e, pertenece p WHERE r.id = p.ID_rutina AND e.id = p.ID_ejercicio AND e.id=?";

    private Connection conn;
    public EjercicioDAO(){
        conn = ConnectionMariaDB.getConnection();
    }
    @Override
    /**  Saves an Exercise entity to the database.
     *
     * @param entity The Exercise entity to be saved.
     * @return The saved Exercise entity.
     */
    public Exercise save(Exercise entity) {
        Exercise result = entity;
            if (entity!=null){


            if (entity.getId()==null){
                        //insert
                        try(PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
                            pst.setInt(1,entity.getSerie());
                            pst.setInt(2,entity.getRepes());
                            pst.setString(3,entity.getName());
                            pst.executeUpdate();
                            ResultSet res = pst.getGeneratedKeys();
                            if(res.next()){
                                entity.setId(res.getInt(1));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }else{
                        try(PreparedStatement pst = conn.prepareStatement(UPDATE)){
                            pst.setInt(1,entity.getSerie());
                            pst.setInt(2,entity.getRepes());
                            pst.setString(3,entity.getName());
                            pst.setInt(4,entity.getId());
                            pst.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }}
        return result;
    }

    @Override
    /** Deletes an Exercise entity from the database
     *
     * @param entity The Exercise entity to be deleted.
     * @return The deleted Exercise entity, or null if deletion fails.
     * @throws SQLException If an SQL error occurs during deletion.
      */
    public Exercise delete(Exercise entity) throws SQLException {
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
    public void close() throws IOException {

    }
    @Override
    /** Finds an Exercise entity by its ID.
     *
     * @param key The ID of the Exercise entity to find.
     * @return The Exercise entity with the specified ID, or null if not found.
     */
    public Exercise findByid(Integer key){
        Exercise result = null;
        if(key != null) {

            try (PreparedStatement pst = conn.prepareStatement(FINDID)) {
                pst.setInt(1, key);
                try (ResultSet res = pst.executeQuery()) {
                    if (res.next()) {
                        Exercise e = new Exercise();
                        e.setId(res.getInt("id"));
                        e.setName(res.getString("name"));
                        e.setSerie(res.getInt("serie"));
                        e.setRepes(res.getInt("repes"));

                        result = e;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**Finds all routines that include a specific Exercise.
     *
     * @param ejercicio The Exercise for which to find routines.
     * @return A list of Routine objects that include the specified Exercise.
     *
     */
    public List<Routine> findRutina (Exercise ejercicio){
        List<Routine> result = new ArrayList<>();
        UsuarioDAO usDAO = new UsuarioDAO();
        if (ejercicio != null){
            try(PreparedStatement pst = conn.prepareStatement(BYRUTINA)){
                pst.setInt(1,ejercicio.getId());
                try(ResultSet res = pst.executeQuery()){
                    while (res.next()){
                        Routine r = new Routine();
                        r.setId(res.getInt("id"));
                        r.setName(res.getString("Descripcion"));
                        r.setUser(usDAO.Identify(res.getInt("ID_usuario")));
                        r.setDay(Dia.valueOf(res.getString("Dia").toUpperCase()));
                        result.add(r);
                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return result;
    }

    /** Builds an instance of EjercicioDAO.
     *
     * @return An instance of EjercicioDAO.
     */
    public static EjercicioDAO build(){
        return new EjercicioDAO();
    }

}
