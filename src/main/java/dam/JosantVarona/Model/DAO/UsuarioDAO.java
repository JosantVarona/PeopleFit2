package dam.JosantVarona.Model.DAO;

import dam.JosantVarona.Model.Connection.ConnectionMariaDB;
import dam.JosantVarona.Model.Entity.Ejercicio;
import dam.JosantVarona.Model.Entity.Usuario;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO  implements DAO<Usuario, Integer>{

    private final static String INSERT = "INSERT INTO usuario (cuenta, pass, name) VALUES(?,?,?)";
    private final static String UPDATE = "UPDATE usuario SET name=? WHERE id=?";
    private final static String FINGBYID = "SELECT u.id,u.cuenta,u.name FROM usuario AS u WHERE u.id=?";
    private final static String FINDBYCUENTAPASS = "SELECT * FROM usuario WHERE cuenta=? AND pass=?";
    private final static String DELETE = "DELETE FROM usuario AS u WHERE u.id";

    @Override
    public Usuario save(Usuario entity) {//HAY QUE CAMBIARLO
        Usuario result = entity;
        if(entity==null || entity.getId()==null) return result;
        Integer id = entity.getId();
            if (id == null){
                Usuario indataBase = findByid(id);
                if (indataBase == null){
                    try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                        pst.setString(1, entity.getCuenta());
                        pst.setString(2,entity.getPass());
                        pst.setString(3,entity.getName());
                        pst.executeUpdate();
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
    public Usuario findByid(Integer primaria) {
    Usuario result = new Usuario();
    if (primaria !=null){
    try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINGBYID)) {
        pst.setInt(1,primaria);
        ResultSet res = pst.executeQuery();
        if (res.next()){
            result.setId(res.getInt("id"));
            result.setCuenta(res.getString("cuenta"));
            result.setName(res.getString("name"));
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
}
