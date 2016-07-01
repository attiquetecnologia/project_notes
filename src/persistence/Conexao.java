/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author usuario
 */
public class Conexao {

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/noteeditor";
    private static final String USER = "rodrigo";
    private static final String SENHA = "rdg";
    
    public static Connection getConexao() {
        Connection conexao = null;
        
        try{
            Class.forName(DRIVER);
            conexao = DriverManager.getConnection(URL,USER,SENHA);
                        
        }catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), 
                    "Erro no carregamento do Driver!", 
                    JOptionPane.ERROR_MESSAGE);
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), 
                    "Problema na conexão!", 
                    JOptionPane.ERROR_MESSAGE);
        }
        
        return conexao;
    }
    
}
