/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto.dao;

import br.com.projeto.jdbc.ConnectionFactory;
import br.com.projeto.model.Clientes;
import br.com.projeto.model.ItemVenda;
import br.com.projeto.model.Produtos;
import br.com.projeto.model.Vendas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuário
 */
public class ItemVendaDAO {
    
    private Connection con;
    
    public ItemVendaDAO(){
    
        this.con = new ConnectionFactory().getConnection();
    }


    //Metodo para cadastrar itens
    public void cadastraItem(ItemVenda obj){
    
        try {
            String sql = "insert into tb_itensvendas(venda_id, produto_id, qtd, subtotal) values(?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, obj.getVenda().getId());
            stmt.setInt(2, obj.getProduto().getId());
            stmt.setInt(3, obj.getQtd());
            stmt.setDouble(4, obj.getSubtotal());
            
            stmt.execute();
            stmt.close();
            
           
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "VENDA NAO REGISTRADA" + e);
        }
    
    }


     public List<ItemVenda> listaItensPorVenda(int venda_id){
        
        try {
            
            List<ItemVenda> lista = new ArrayList<>();
            
            
            String sql = "select i.id, p.descricao, i.qtd, p.preco, i.subtotal from tb_itensvendas as i inner join tb_produtos as p on(i.produto_id = p.id) where i.venda_id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, venda_id);

            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
            
                ItemVenda item = new ItemVenda();
                Produtos produto = new Produtos();
                
                
                item.setId(rs.getInt("i.id"));
                produto.setDescricao(rs.getString("p.descricao"));
                item.setQtd(rs.getInt("i.qtd"));
                produto.setPreco(rs.getDouble("p.preco"));
                item.setSubtotal(rs.getDouble("i.subtotal"));
                item.setProduto(produto);
                
                
                lista.add(item);
            }
            return lista;
            
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
            return null;
        }
        
    }

}
