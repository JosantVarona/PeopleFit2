package dam.JosantVarona.Model.DAO;

import dam.JosantVarona.Model.Connection.ConnectionMariaDB;
import dam.JosantVarona.Model.Entity.Ejercicio;
import dam.JosantVarona.Model.Entity.Rutina;
import dam.JosantVarona.Model.Entity.Usuario;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO  implements DAO<Usuario, String>{

    private final static String INSERT = "INSERT INTO usuario (cuenta, pass, name) VALUES(?,?,?)";
    private final static String UPDATE = "UPDATE usuario SET name=? WHERE id=?";
    private final static String FINGBYCUENTA = "SELECT u.id,u.cuenta,u.name,u.pass FROM usuario AS u WHERE u.cuenta=?";
    private final static String FINDBYCUENTAPASS = "SELECT u.cuenta,u.pass FROM usuario AS u WHERE u.cuenta=?";
    private final static String DELETE = "DELETE FROM usuario AS u WHERE u.id";
    private final static String FINGBYID = "SELECT u.id,u.cuenta,u.name,u.pass FROM usuario AS u WHERE u.id=?";

    @Override
    public Usuario save(Usuario entity) {
        Usuario result = entity;
        if(entity!=null || entity.getCuenta() !=null){
        Usuario u = findByid(entity.getCuenta());
                        if (u.getCuenta() == null) {
                            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                                pst.setString(1, entity.getCuenta());
                                pst.setString(2, entity.getPass());
                                pst.setString(3, entity.getName());
                                pst.executeUpdate();
                                ResultSet res = pst.getGeneratedKeys();
                                if (res.next()) {
                                    entity.setId(res.getInt(1));
                                }
                                if (entity.getRutinas() != null) {
                                    for (Rutina r : entity.getRutinas()) {
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
                                if (entity.getRutinas() != null) {
                                    List<Rutina> rutinasBefore = RutinaDAO.build().findByUsuario(entity);
                                    List<Rutina> rutinaAfter = entity.getRutinas();
                                    List<Rutina> rutinaToBeRemoved = new ArrayList<>(rutinasBefore);
                                    rutinaToBeRemoved.removeAll(rutinaAfter);
                                    for (Rutina r : rutinaToBeRemoved) {
                                        RutinaDAO.build().delete(r);
                                    }
                                    for (Rutina r : rutinaAfter) {
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
    public Usuario delete(Usuario entity) throws SQLException {
        if(entity==null ) return entity;
        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setInt(1,entity.getId());
            pst.executeUpdate();
        }
        return entity;
    }

    @Override
    public Usuario findByid(String primaria) {
    Usuario result = new Usuario();
    if (primaria !=null){
    try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINGBYCUENTA)) {
        pst.setString(1,primaria);
        ResultSet res = pst.executeQuery();
        if (res.next()){
            result.setId(res.getInt("id"));
            result.setCuenta(res.getString("cuenta"));
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
    public Usuario Identificardor(Integer id) {
        Usuario result = new Usuario();
        if (id !=null){
            try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINGBYID)) {
                pst.setInt(1,id);
                ResultSet res = pst.executeQuery();
                if (res.next()){
                    result.setId(res.getInt("id"));
                    result.setCuenta(res.getString("cuenta"));
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
    public Usuario verifi(String cuenta){
        Usuario result=null;
        if (cuenta !=null){
            try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYCUENTAPASS)) {
                pst.setString(1,cuenta);
                ResultSet res = pst.executeQuery();
                if (res.next()){
                    result.setCuenta(res.getString("cuenta"));
                    result.setPass(res.getString("pass"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
class UsuarioLazy extends Usuario{
    @Override
    public List<Rutina> getRutinas(){
        if(super.getRutinas()==null){
            setRutinas(RutinaDAO.build().findByUsuario(this));
        }
        return super.getRutinas();
    }
}