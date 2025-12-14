package modelo;

import java.util.Date;

public class Receita extends MovimentacaoFinanceira {
    private String fonte;
    
    public Receita(double valor, Date data, String descricao, Categoria categoria, String fonte) {
        super(valor, data, descricao, categoria);
        this.fonte = fonte;
    }
    
    public String getFonte() { return fonte; }
    public void setFonte(String fonte) { this.fonte = fonte; }
    
    @Override
    public String getTipo() {
        return "RECEITA";
    }
    
    @Override
    public String toString() {
        return String.format("Receita: %s | Valor: R$ %.2f | Data: %s | Fonte: %s | Categoria: %s",
                descricao, valor, getDataFormatada(), fonte, categoria.getNome());
    }
    
    @Override
    public String toFileString() {
        return String.format("RECEITA|%.2f|%s|%s|%d|%s",
                valor, getDataFormatada(), descricao, categoria.getId(), fonte);
    }
}