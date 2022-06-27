package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Usuario;

/**
 *
 * @author Sávio Campos
 */
public class UsuarioDAO {
    Connection conn;
    
    public ResultSet authentication(Usuario user) throws SQLException {
        conn = new ConnectionDAO().connection();
        String sql = "SELECT * FROM tbl_usuario WHERE username = ? AND pass = ?";
        
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            
            ResultSet rset = statement.executeQuery();
            return rset;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
}