package controller;

import dao.UsuarioDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Usuario;
import view.TelaLogin;
import view.TelaPrincipal;

/**
 *
 * @author Sávio Campos
 */
public class LoginController {
    private final TelaLogin login;
    
    public LoginController(TelaLogin login) {
        this.login = login;
    }
    
    public void login() throws SQLException {
        String username = login.getTxtUsername().getText();
        String password = login.getTxtPassword().getText();

        Usuario user = new Usuario();
        user.setUsername(username);
        user.setPassword(password);
        
        UsuarioDAO userdao = new UsuarioDAO();
        
        ResultSet reset = userdao.authentication(user);
        
        if(reset.next()) {
            TelaPrincipal main = new TelaPrincipal();
            main.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuário ou senha inválida");
        }
    }
}