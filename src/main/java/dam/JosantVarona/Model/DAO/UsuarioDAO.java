package dam.JosantVarona.Model.DAO;

import dam.JosantVarona.Model.Connection.ConnectionMariaDB;
import dam.JosantVarona.Model.Entity.Routine;
import dam.JosantVarona.Model.Entity.User;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO  implements DAO<User, String>{

    private final static String INSERT = "INSERT INTO usuario (cuenta, pass, name) VALUES(?,?,?)";
    private final static String UPDATE = "UPDATE usuario SET name=? WHERE id=?";
    private final static String FINGBYCUENTA = "SELECT u.id,u.cuenta,u.name,u.pass FROM usuario AS u WHERE u.cuenta=?";
    private final static String FINDBYCUENTAPASS = "SELECT u.cuenta,u.pass FROM usuario AS u WHERE u.cuenta=?";
    private final static String DELETE = "DELETE FROM usuario WHERE id=?";
    private final static String FINGBYID = "SELECT u.id,u.cuenta,u.name,u.pass FROM usuario AS u WHERE u.id=?";

    @Override
    public User save(User entity) {
        User result = entity;
        if(entity!=null || entity.getAccount() !=null){
        User u = findByid(entity.getAccount());
                        if (u.getAccount() == null) {
                            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                                pst.setString(1, entity.getAccount());
                                pst.setString(2, entity.getPass());
                                pst.setString(3, entity.getName());
                                pst.executeUpdate();
                                ResultSet res = pst.getGeneratedKeys();
                                if (res.next()) {
                                    entity.setId(res.getInt(1));
                                }
                                if (entity.getRoutines() != null) {
                                    for (Routine r : entity.getRoutines()) {
                                        RutinaDAO.build().save(r);
                                    }
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }


                        } else {
                            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
                                pst.setString(1, entity.getName());
                                pst.setInt(2, entity.getId());
                                pst.executeUpdate();
                                if (entity.getRoutines() != null) {
                                    List<Routine> rutinasBefore = RutinaDAO.build().findByUsuario(entity);
                                    List<Routine> rutinaAfter = entity.getRoutines();
                                    List<Routine> rutinaToBeRemoved = new ArrayList<>(rutinasBefore);
                                    rutinaToBeRemoved.removeAll(rutinaAfter);
                                    for (Routine r : rutinaToBeRemoved) {
                                        RutinaDAO.build().delete(r);
                                    }
                                    for (Routine r : rutinaAfter) {
                                        RutinaDAO.build().save(r);
                                    }
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }


        }
        return result;
    }

    @Override
    public User delete(User entity) throws SQLException {
        if(entity==null ) return entity;
        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setInt(1,entity.getId());
            pst.executeUpdate();
        }
        return entity;
    }

    @Override
    public User findByid(String primaria) {
    User result = new User();
    if (primaria !=null){
    try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINGBYCUENTA)) {
        pst.setString(1,primaria);
        ResultSet res = pst.executeQuery();
        if (res.next()){
            result.setId(res.getInt("id"));
            result.setAccount(res.getString("cuenta"));
            result.setName(res.getString("name"));
            result.setPass(res.getString("pass"));
        }
        res.close();
    } catch (SQLException e) {
        e.printStackTrace();
        }
    }
    return result;
    }
    public User Identify(Integer id) {
        User result = new User();
        if (id !=null){
            try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINGBYID)) {
                pst.setInt(1,id);
                ResultSet res = pst.executeQuery();
                if (res.next()){
                    result.setId(res.getInt("id"));
                    result.setAccount(res.getString("cuenta"));
                    result.setName(res.getString("name"));
                    result.setPass(res.getString("pass"));
                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    @Override
    public void close() throws IOException {

    }
    public User verifi(String cuenta){
        User result=null;
        if (cuenta !=null){
            try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYCUENTAPASS)) {
                pst.setString(1,cuenta);
                ResultSet res = pst.executeQuery();
                if (res.next()){
                    result.setAccount(res.getString("cuenta"));
                    result.setPass(res.getString("pass"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
class UsuarioLazy extends User {
    @Override
    public List<Routine> getRoutines(){
        if(super.getRoutines()==null){
            setRoutines(RutinaDAO.build().findByUsuario(this));
        }
        return super.getRoutines();
    }
}