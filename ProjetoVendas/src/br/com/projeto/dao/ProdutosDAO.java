/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto.dao;

import br.com.projeto.jdbc.ConnectionFactory;
import br.com.projeto.model.Clientes;
import br.com.projeto.model.Fornecedores;
import br.com.projeto.model.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author Usuário
 */
public class ProdutosDAO {
    
    
    private Connection con;
    
    public ProdutosDAO(){
    
        this.con = new ConnectionFactory().getConnection();
    
    }
    
    //metodo cadastrar produtos
    public void cadastrarProdutos(Produtos obj){
    
        try {
            String sql = "insert into tb_produtos(descricao, preco, qtd_estoque, for_id) values(?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtd_estoque());
            stmt.setInt(4, obj.getFornecedor().getId());
            
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "PRODUTO CADASTRADO");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "PRODUTO NAO CADASTRADO" + e);
        }
    
    }
    
    public List<Produtos> listarProdutos(){
        
        try {
            
            List<Produtos> lista = new ArrayList<>();
            
            
            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p inner join tb_fornecedores as f on (p.for_id = f.id)";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
            
                Produtos obj = new Produtos(); 
                Fornecedores f = new Fornecedores();
                
                obj.setId(rs.getInt("p.id")); 
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(Double.parseDouble(rs.getString("p.preco")));
                obj.setQtd_estoque(Integer.parseInt(rs.getString("p.qtd_estoque")));
                
                f.setNome(rs.getString("f.nome"));
                obj.setFornecedor(f);
                lista.add(obj);
            }
            return lista;
            
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
            return null;
        }
        
    }
    
    public void alterarProduto(Produtos obj){
        
        try {
//          

              String sql = "update tb_produtos set descricao=?, preco=?, qtd_estoque=?, for_id=? where id=?";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtd_estoque());
            stmt.setInt(4, obj.getFornecedor().getId());
            stmt.setInt(5, obj.getId());
            
            
            
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "ALTERADO");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "NAO ALTERADO: " +e);
        }
        
    }
    
    public void excluirProduto(Produtos obj){
    
        try {
            String sql = "delete from tb_produtos where id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, obj.getId());
            
            
            
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "EXCLUIDO");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "NAO EXCLUIDO: " +e);
        }
    
    
    }
    
    public List<Produtos> listarProdutosPorNome(String nome){
        
        try {
            
            List<Produtos> lista = new ArrayList<>();
            
            
            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p inner join tb_fornecedores as f on (p.for_id = f.id) where p.descricao like ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
            
                Produtos obj = new Produtos(); 
                Fornecedores f = new Fornecedores();
                
                obj.setId(rs.getInt("p.id")); 
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(Double.parseDouble(rs.getString("p.preco")));
                obj.setQtd_estoque(Integer.parseInt(rs.getString("p.qtd_estoque")));
                
                f.setNome(rs.getString("f.nome"));
                obj.setFornecedor(f);
                lista.add(obj);
            }
            return lista;
            
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
            return null;
        }
        
    }
    
    public Produtos consultaPorNome(String nome){
        
        try {
        
                
            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p inner join tb_fornecedores as f on (p.for_id = f.id) where p.descricao=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            
            Produtos obj = new Produtos(); 
            Fornecedores f = new Fornecedores();
            if(rs.next()){

                obj.setId(rs.getInt("p.id")); 
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(Double.parseDouble(rs.getString("p.preco")));
                obj.setQtd_estoque(Integer.parseInt(rs.getString("p.qtd_estoque")));
                
                f.setNome(rs.getString("f.nome"));
                obj.setFornecedor(f);
               
            }
            return obj;
            
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
            return null;
        }
        
    }
    
    public Produtos buscaPorCodigo(String id){
        
        try {
        
                
            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p inner join tb_fornecedores as f on (p.for_id = f.id) where p.id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            
            Produtos obj = new Produtos(); 
            Fornecedores f = new Fornecedores();
            if(rs.next()){

                obj.setId(rs.getInt("p.id")); 
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(Double.parseDouble(rs.getString("p.preco")));
                obj.setQtd_estoque(Integer.parseInt(rs.getString("p.qtd_estoque")));
                
                f.setNome(rs.getString("f.nome"));
                obj.setFornecedor(f);
               
            }
            return obj;
            
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
            return null;
        }
        
    }
    
    
    
   //Dar baixa no estoque
    public void baixaEstoque(int id, int qtd_nova){
    
        try {
            String sql = "update tb_produtos set qtd_estoque=? where id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, qtd_nova);
            stmt.setInt(2, id);
            stmt.execute();
            stmt.close();
            //JOptionPane.showMessageDialog(null, "QTD atualizada com sucesso");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possivel atualizar estoque");
        }
    
    
    }
    
    public void adicionarEstoque(int id, int qtd_nova){
    
        try {
            String sql = "update tb_produtos set qtd_estoque=? where id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, qtd_nova);
            stmt.setInt(2, id);
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "QTD atualizada com sucesso");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possivel atualizar estoque");
        }
    
    
    }
    
  //retorna o estoque atual de um produto
    public int retornaEstoqueAtual(int id){
    
        try {
            int qtd_estoque=0;
            
            String sql = "select qtd_estoque from tb_produtos where id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
           
                qtd_estoque = (rs.getInt("qtd_estoque"));
                
            }
            return qtd_estoque;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    
    }
    
    
    
    
    
    
    
    
    
    
}
