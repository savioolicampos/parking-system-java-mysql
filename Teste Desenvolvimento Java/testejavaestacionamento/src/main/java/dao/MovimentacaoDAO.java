package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Movimentacao;
import model.Valor;

/**
 *
 * @author Sávio Campos
 */
public class MovimentacaoDAO {
    Connection conn;
    PreparedStatement statement;
    ResultSet rs;
    
    public void inserir(Movimentacao movimentacao) throws SQLException {
        conn = new ConnectionDAO().connection();
        String sql = "INSERT INTO tbl_movimentacao (placa, modelo, data_entrada, tempo, valor_pago)"
                + "VALUES (?, ?, ?, ?, ?)";
        
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, movimentacao.getPlaca());
            statement.setString(2, movimentacao.getModelo());
            statement.setString(3, movimentacao.getDtEntrada());
            statement.setString(4, movimentacao.getTempo());
            statement.setString(5, movimentacao.getValorPago());
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public void alterarMovimentacao(Movimentacao movimentacao) throws SQLException {
        conn = new ConnectionDAO().connection();
        String sql = "UPDATE tbl_movimentacao SET placa = ?, modelo = ?, data_entrada = ? WHERE id = ?";
        
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, movimentacao.getPlaca());
            statement.setString(2, movimentacao.getModelo());
            statement.setString(3, movimentacao.getDtEntrada());
            statement.setInt(4, movimentacao.getId());
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public void adicionarSaida(Movimentacao movimentacao) throws SQLException {
        conn = new ConnectionDAO().connection();
        String sql = "UPDATE tbl_movimentacao SET data_saida = ? WHERE id = ?";
        
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, movimentacao.getDtSaida());
            statement.setInt(2, movimentacao.getId());
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public ResultSet adicionarTempo(int id) throws SQLException {
        conn = new ConnectionDAO().connection();
        String sql = "UPDATE tbl_movimentacao SET tempo = timediff(data_saida, data_entrada) WHERE id = ?";
        
        try {
            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
            statement.close();
            return rs;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return null;
        }
    }
    
    public void adicionarValor(int id) throws SQLException {
        String tempo = listarTempo(id);
        
        conn = new ConnectionDAO().connection();
        String sql = "UPDATE tbl_movimentacao SET valor_pago = calcular_valor(6.00, 4.00, ?) WHERE id = ?";
        statement = conn.prepareStatement(sql);
        statement.setString(1, tempo);
        statement.setInt(2, id);
        statement.execute();
        statement.close();
    }

    private Valor listarValores() throws SQLException {
        conn = new ConnectionDAO().connection();
        String sql = "SELECT * FROM tbl_valor WHERE id = 1";
        
        try {
            statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            Valor valor = new Valor();
            valor.setValorPrimeiraHora(rs.getString("valor_primeira_hora"));
            valor.setValorPrimeiraHora(rs.getString("valor_demais_horas"));
            return valor;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
    
    public String listarTempo(int id) throws SQLException {
        conn = new ConnectionDAO().connection();
        String sql = "SELECT tempo FROM tbl_movimentacao WHERE id = ?";
        
        try {
            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            return rs.getString("tempo");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
    
    public void excluir(Movimentacao movimentacao) throws SQLException {
        conn = new ConnectionDAO().connection();
        String sql = "DELETE FROM tbl_movimentacao WHERE id = ?";
        
        try {
            statement = conn.prepareStatement(sql);
            statement.setInt(1, movimentacao.getId());
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public ArrayList<Movimentacao> listarMovimentacoes() throws SQLException {
        ArrayList<Movimentacao> lista = new ArrayList<>();
        conn = new ConnectionDAO().connection();
        
        String sql = "SELECT * FROM tbl_movimentacao";
        
        try {
            statement = conn.prepareStatement(sql);
            rs = statement.executeQuery();
            
            while (rs.next()) {
                Movimentacao mov = new Movimentacao();
                mov.setId(rs.getInt("id"));
                mov.setPlaca(rs.getString("placa"));
                mov.setModelo(rs.getString("modelo"));
                mov.setDtEntrada(rs.getString("data_entrada"));
                mov.setDtSaida(rs.getString("data_saida"));
                mov.setTempo(rs.getString("tempo"));
                mov.setValorPago(rs.getString("valor_pago"));
                
                lista.add(mov);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        return lista;
    }
}