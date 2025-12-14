package modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class MovimentacaoFinanceira {
    protected double valor;
    protected Date data;
    protected String descricao;
    protected Categoria categoria;
    
    public MovimentacaoFinanceira(double valor, Date data, String descricao, Categoria categoria) {
        this.valor = valor;
        this.data = data;
        this.descricao = descricao;
        this.categoria = categoria;
    }
    
    // Getters e Setters
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    
    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    
    public String getDataFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }
    
    public abstract String getTipo();
    
    // Método para formato de arquivo
    public abstract String toFileString();
}