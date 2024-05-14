package dam.JosantVarona.Model.DAO;

import dam.JosantVarona.Model.Connection.ConnectionMariaDB;
import dam.JosantVarona.Model.Entity.Ejercicio;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class EjercicioDAO implements DAO<Ejercicio, Integer>{
    private static final String INSERT = "INSERT INTO ejercicio(serie,repes,name) VALUES (?,?,?)";
    private static final String FINDBNAME = "SELECT e.name,e.serie,e.repes,e.id FROM ejercicio AS e WHERE e.name=?";
    private static final String FINDID = "SELECT e.id,e.name,e.serie,e.repes FROM ejercicio AS e WHERE e.id=?";
    private static final String UPDATE = "UPDATE ejercicio SET serie=?,repes=?,name=? WHERE id=?";
    private static final String DELETE = "DELETE FROM ejercicio WHERE id=?";

    private Connection conn;
    public EjercicioDAO(){
        conn = ConnectionMariaDB.getConnection();
    }
    @Override
    public Ejercicio save(Ejercicio entity) {
        Ejercicio result = entity;
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
    public Ejercicio delete(Ejercicio entity) throws SQLException {
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
    public Ejercicio findByid(Integer key){
        Ejercicio result = null;
        if(key != null) {

            try (PreparedStatement pst = conn.prepareStatement(FINDID)) {
                pst.setInt(1, key);
                try (ResultSet res = pst.executeQuery()) {
                    if (res.next()) {
                        Ejercicio e = new Ejercicio();
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
    public EjercicioDAO build(){
        return new EjercicioDAO();
    }
}
