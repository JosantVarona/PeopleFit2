package dam.JosantVarona.Model.DAO;

import dam.JosantVarona.Model.Connection.ConnectionMariaDB;
import dam.JosantVarona.Model.Entity.Multimedia;
import dam.JosantVarona.Model.Entity.Photos;
import dam.JosantVarona.Model.Entity.Video;

import java.io.IOException;
import java.sql.*;


public class MultimediaDAO implements DAO<Multimedia, Integer>{
    private static final String INSERT = "INSERT INTO multimedia(id_ejercicio,nombre,extension,tipo,datos) VALUES (?,?,?,?,?)";
    private static final String FINDBYEJER = "SELECT m.id,m.datos,m.tipo FROM multimedia AS m WHERE m.id_ejercicio=?";
    private static final String DELETE = "DELETE FROM multimedia WHERE id=?";

    private Connection conn;
    public MultimediaDAO(){
        conn = ConnectionMariaDB.getConnection();
    }
    @Override
    public Multimedia save(Multimedia entity) {
        Multimedia result = entity;
       if (entity != null){
           if (entity instanceof Photos){
            try(PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
                pst.setInt(1,entity.getExercise().getId());
                pst.setString(2, entity.getName());
                pst.setString(3,entity.getExtension());
                pst.setString(4,entity.getType());
                pst.setBytes(5,entity.getData());
                pst.executeUpdate();
                ResultSet res = pst.getGeneratedKeys();
                if (res.next()){
                    entity.setId(res.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
           }else if (entity instanceof Video){
               try(PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
                   pst.setInt(1,entity.getExercise().getId());
                   pst.setString(2, entity.getName());
                   pst.setString(3,entity.getExtension());
                   pst.setString(4,entity.getType());
                   pst.setBytes(5,entity.getData());
                   pst.executeUpdate();
                   ResultSet res = pst.getGeneratedKeys();
                   if (res.next()){
                       entity.setId(res.getInt(1));
                   }


           } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
           }

        return result;
    }

    @Override
    public Multimedia delete(Multimedia entity) throws SQLException {
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
    public Multimedia findByid(Integer primaria) {
        Multimedia result = null;
        if (primaria !=null){
            try (PreparedStatement pst = conn.prepareStatement(FINDBYEJER)){
                pst.setInt(1, primaria);
                try(ResultSet res = pst.executeQuery()){
                    if (res.next()){
                        result = new Multimedia();
                        result.setId(res.getInt("id"));
                        result.setData(res.getBytes("datos"));
                        result.setType(res.getString("tipo"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public void close() throws IOException {

    }

}
