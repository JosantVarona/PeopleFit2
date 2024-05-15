package dam.JosantVarona.Model.DAO;

import dam.JosantVarona.Model.Connection.ConnectionMariaDB;
import dam.JosantVarona.Model.Entity.Ejercicio;
import dam.JosantVarona.Model.Entity.Rutina;
import dam.JosantVarona.Model.Enum.Dia;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EjercicioDAO implements DAO<Ejercicio, Integer>{
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
    public List<Rutina> findRutina (Ejercicio ejercicio){
        List<Rutina> result = new ArrayList<>();
        UsuarioDAO usDAO = new UsuarioDAO();
        if (ejercicio != null){
            try(PreparedStatement pst = conn.prepareStatement(BYRUTINA)){
                pst.setInt(1,ejercicio.getId());
                try(ResultSet res = pst.executeQuery()){
                    while (res.next()){
                        Rutina r = new Rutina();
                        r.setId(res.getInt("id"));
                        r.setDescripcion(res.getString("Descripcion"));
                        r.setUsuario(usDAO.Identificardor(res.getInt("ID_usuario")));
                        r.setDia(Dia.valueOf(res.getString("Dia").toUpperCase()));
                        result.add(r);
                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return result;
    }
    public static EjercicioDAO build(){
        return new EjercicioDAO();
    }

}
