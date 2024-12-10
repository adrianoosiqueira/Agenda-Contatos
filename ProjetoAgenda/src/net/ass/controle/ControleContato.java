package net.ass.controle;

import java.util.List;
import net.ass.modelos.classes.Contato;
import net.ass.modelos.interfaces.IContatoCRUD;
import javax.swing.JOptionPane;
import net.ass.modelos.persistencia.ContatoDAO;

public class ControleContato implements IContatoCRUD {
    
    IContatoCRUD persistenciaContato = null;

    public ControleContato() throws Exception{
        try{
        persistenciaContato = new ContatoDAO();
        }catch (Exception erro){
            throw erro;
        }
    }

    @Override
    public void incluir(Contato pessoa) throws Exception {
        try{
           persistenciaContato.incluir(pessoa);
        }catch (Exception erro) {
            throw erro;
        } 
    }

    @Override
    public void alterar(Contato pessoa) throws Exception {
        try{
            persistenciaContato.alterar(pessoa);
        }catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public void excluir(int identificador) throws Exception {
        try{
           persistenciaContato.excluir(identificador); 
        }catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public Contato consultar(int identificador) throws Exception {
        try {
            // Recupera o contato pela persistência e retorna o valor
            return persistenciaContato.consultar(identificador);
        } catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public Contato consultar(String nome) throws Exception {
        try {
            // Recupera o contato pelo nome e retorna o valor
            return persistenciaContato.consultar(nome);
        } catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public List<Contato> ListagemDeContatos() throws Exception {
        try {
            return persistenciaContato.ListagemDeContatos(); // Retorna a lista de contatos da persistência
        } catch (Exception erro) {
            throw erro;
        }
    }
}
