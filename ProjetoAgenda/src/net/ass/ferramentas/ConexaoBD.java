/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ass.ferramentas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author adria
 */
public class ConexaoBD {
    private static Connection conexao = null;  
  private ConexaoBD(){}
  public static Connection getConexao() throws Exception{
    try{
      if(conexao == null){
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost:5433/AulaDB";
        String user = "postgres";
        String password = "motogyn171";
                  
        Class.forName(driver);
        conexao = DriverManager.getConnection(url, user, password);
      }
               
    }
    catch(ClassNotFoundException erro){
      //Erro de não encontrar o drive do banco no projeto
      throw new Exception("Drive: "+erro.getMessage());
    }
    catch(SQLException erro){
      //Erro no banco de dados: usuario, senha ou banco de dados 
      throw new Exception("Banco: " + erro.getMessage());
    } 
    return conexao;      
  }
}
