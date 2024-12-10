/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package net.ass.modelos.interfaces;
import net.ass.modelos.classes.Contato;
import java.util.List;

/**
 *
 * @author adria
 */
public interface IContatoCRUD {
    public void incluir(Contato pessoa) throws Exception;
    public void alterar(Contato pessoa) throws Exception;
    public void excluir(int identificador) throws Exception;
    public Contato consultar(int identificador) throws Exception;
    public Contato consultar(String nome) throws Exception;
    public List<Contato> ListagemDeContatos()throws Exception;
    
}
