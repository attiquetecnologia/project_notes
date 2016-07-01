

package persistence;
 
//import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
 

/**
 * <p>Atenção!
 * Generic dao não pode ser usado diretamente, ele precisa
 * de uma classe DAO especifica para invocála.
 * Esta classe apenas contém os métodos executar, para insert,delete e update.
 * @author RODIROG ATTIQUE SANTANA
 * rodrigoatique@gmail.com
 * @version 1.1 01.04.2015
 * Foi adicionado duas novas funçoes para executar
 * @version 1.0 02.02.2015
 * 
 */
public abstract class GenericDAO {
    private Connection connection;
 
    public GenericDAO() {
        this.connection = Conexao.getConexao();
    }
    
    public Connection getConnection()  {
        
        return connection;
    }
    public void setConnection(Connection connection){
        this.connection = connection;
    }
    
    /**
     * Executar.
     * Este método pode ser usado para fazer as tres operações básicas
     * do ddl, insert,update e delete. Sendo posssível usar também uma procedure
     * para salvar.
     * @param isProcedure
     * true - é uma procedure
     * Executa um prepareCall
     * false - não é uma procedure
     * Executa um prepareStatement
     * @param sql
     * String contendo a instrução para inserção, delete ou update.
     * @param params
     * Parâmetros de comparação como chaves, nomes ...
     * @since 1.0
     * @return
     * retorna false se não executar a instrução e verdadeiro se executar.
     * @throws SQLException 
     * Retorna um erro de sql
     */
    protected boolean executar(boolean isProcedure,String sql, Object... params) 
            throws SQLException{
        PreparedStatement ps;
        if (isProcedure)
            return isProcedure(sql, params);
        else ps = getConnection().prepareStatement(sql);
        for (int i=0;i<params.length;i++){
            ps.setObject(i+1, params[i]);
        }
//        System.out.println(ps.toString());
        if(ps.executeUpdate()>0){
            ps.close();
            return true;
        }
        return false;
    }//fim executar
    
    /**
     * Função auxiliar para executar uma procedure em executar.
     * @param sql
     * String contendo a instrução para inserção, delete ou update.
     * @param params
     * Parâmetros de comparação como chaves, nomes ...
     * @throws SQLException 
     * since 1.1
     */
    private boolean isProcedure(String sql,Object... params) throws SQLException{
        PreparedStatement ps = getConnection().prepareCall(sql);
        for (int i=0;i<params.length;i++){
            ps.setObject(i+1, params[i]);
        }
        if(ps.execute()){
            ps.close();
            return true;
        }
        return false;
    }
    
    /**
     * Executa uma instrução sql e retonra uma id inserida
     * @param sql
     * String contendo a instrução para inserção, delete ou update.
     * @param parametros 
     * Parâmetros de comparação como chaves, nomes ...
     * @return 
     * Retonra o ID da instrução
     * @throws SQLException
     * Retonra um erro de sql
     * @since 1.0
     */
    public Integer executar(String sql,Object... parametros) throws SQLException{
        Integer key = 0;
        PreparedStatement ps = getConnection().prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < parametros.length; i++) {
            ps.setObject(i+1, parametros[i]);
        }//fim for

        ps.executeUpdate();
       
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next())
            key = rs.getInt(1);
        ps.close();
        return key;
    }// fim executar
}// fim classe