/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ass.modelos.persistencia;
import java.util.List;
import net.ass.modelos.interfaces.IContatoCRUD;
import net.ass.modelos.classes.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
// Classe de conexao
import net.ass.ferramentas.ConexaoBD;
import net.ass.modelos.enumeracao.enumSexo;
/**
 *
 * @author adria
 */
public class ContatoDAO implements IContatoCRUD{
    //Conexao com o banco
    private Connection conexao = null;
    public ContatoDAO() throws Exception{
      conexao = ConexaoBD.getConexao();
      if(conexao == null) throw new Exception("ERRO DE CONEXAO");
    }

    @Override
    public void incluir(Contato pessoa) throws Exception {
        try {
        String sql =  "insert into contatos(nome, ddi, ddd, numero, sexo, email)"
                +     "values(?,?,?,?,?,?);";
        //Criando vinculo entre o comando SQL e o SGBD
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setString(1, pessoa.getNome());
        preparedStatement.setInt(2, pessoa.getFone().getDdi());
        preparedStatement.setInt(3, pessoa.getFone().getDdd());
        preparedStatement.setInt(4,pessoa.getFone().getNumero());
        preparedStatement.setString(5,pessoa.getSexo().toString());
        preparedStatement.setString(6,pessoa.getEmail());
        // Mandando o comando SQL para SGBD executar
        preparedStatement.executeUpdate();
      } catch (SQLException erro) {
          //Erro do comando SQL - chave, coluna, nome da tabela, ...
          throw new Exception("SQL Erro: "+ erro.getMessage());
      } catch(Exception erro){
            throw new Exception("Incluir Persistencia: " + erro);
      }
    }

    @Override
    public void alterar(Contato pessoa) throws Exception {
        String sql = "UPDATE Contatos SET nome = ?, ddi = ?, ddd = ?,"
                + " numero = ?, sexo = ?, email = ? WHERE idContato = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            // Configurando os parâmetros do PreparedStatement
            preparedStatement.setString(1, pessoa.getNome());
            preparedStatement.setInt(2, pessoa.getFone().getDdi());
            preparedStatement.setInt(3, pessoa.getFone().getDdd());
            preparedStatement.setInt(4, pessoa.getFone().getNumero());
            preparedStatement.setString(5, pessoa.getSexo().toString());
            preparedStatement.setString(6, pessoa.getEmail());
            preparedStatement.setInt(7, pessoa.getIdContato());

            // Executando a atualização no banco de dados
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Nenhum contato foi alterado. Verifique se o ID está correto.");
            }
        } catch (SQLException erro) {
            // Lançando uma exceção personalizada para o erro SQL
            throw new Exception("Erro SQL ao tentar alterar o contato: " + erro.getMessage());
        }
    }


    @Override
    public void excluir(int identificador) throws Exception {
        try {
          PreparedStatement preparedStatement = null;
          preparedStatement = conexao.prepareStatement("delete from Contatos where idContato=?");
          // Parameters start with 1
          preparedStatement.setInt(1, identificador);
          preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }   
    }

    
    @Override
    public Contato consultar(int identificador) throws Exception {
        Contato contato = null;
        String sql = "SELECT * FROM contatos WHERE idContato = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, identificador); // Define o ID na consulta

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Preenche o objeto Contato com os dados do banco
                contato = new Contato();
                contato.setIdContato(rs.getInt("idContato"));
                contato.setNome(rs.getString("nome"));
                contato.setEmail(rs.getString("email"));

                // Verifica se o telefone é nulo e inicializa o objeto Telefone
                Telefone fone = new Telefone();
                fone.setDdi(rs.getInt("ddi"));
                fone.setDdd(rs.getInt("ddd"));
                fone.setNumero(rs.getInt("numero"));
                contato.setFone(fone);  // Atribui o telefone ao contato

                // Adicione outros campos, como o sexo, se necessário
                contato.setSexo(enumSexo.valueOf(rs.getString("sexo")));
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao consultar o contato", e);
        }

        return contato; // Retorna o objeto encontrado ou null
    }
    
    @Override
    public Contato consultar(String nome) throws Exception {
        Contato contato = null;
        String sql = "SELECT * FROM contatos WHERE nome LIKE ?";  // Usando LIKE para buscar o nome de forma mais flexível

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");  // Adiciona o % para buscar nomes que contenham a string fornecida

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Preenche o objeto Contato com os dados do banco
                contato = new Contato();
                contato.setIdContato(rs.getInt("idContato"));
                contato.setNome(rs.getString("nome"));
                contato.setEmail(rs.getString("email"));

                // Verifica se o telefone é nulo e inicializa o objeto Telefone
                Telefone fone = new Telefone();
                fone.setDdi(rs.getInt("ddi"));
                fone.setDdd(rs.getInt("ddd"));
                fone.setNumero(rs.getInt("numero"));
                contato.setFone(fone);  // Atribui o telefone ao contato

                // Adicione outros campos, como o sexo, se necessário
                contato.setSexo(enumSexo.valueOf(rs.getString("sexo")));
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao consultar o contato pelo nome.", e);
        }

        return contato; // Retorna o objeto encontrado ou null
    }


   @Override
    public List<Contato> ListagemDeContatos() throws Exception {
    List<Contato> listaDeContatos = new LinkedList<>();
    String sql = "select * from Contatos order by idContato";
    try {
      Statement statement = conexao.createStatement();
      ResultSet rs = statement.executeQuery(sql);
      while(rs.next()) {
          Contato objContato = new Contato();
          objContato.setIdContato(rs.getInt("idContato"));
          objContato.setNome(rs.getString("nome"));
          Telefone fone = new Telefone();
          fone.setDdi(rs.getInt("ddi"));
          fone.setDdd(rs.getInt("ddd"));
          fone.setNumero(rs.getInt("numero"));
          objContato.setFone(fone);
          objContato.setSexo(enumSexo.valueOf(rs.getString("sexo")));
          objContato.setEmail(rs.getString("email"));
          listaDeContatos.add(objContato);
      }
      return listaDeContatos;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;    
  }

}