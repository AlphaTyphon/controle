package modelo;

import java.util.Date;

public class Despesa extends MovimentacaoFinanceira {
    private boolean fixa;
    
    public Despesa(double valor, Date data, String descricao, Categoria categoria, boolean fixa) {
        super(valor, data, descricao, categoria);
        this.fixa = fixa;
    }
    
    public boolean isFixa() { return fixa; }
    public void setFixa(boolean fixa) { this.fixa = fixa; }
    
    @Override
    public String getTipo() {
        return "DESPESA";
    }
    
    public String getTipoDespesa() {
        return fixa ? "Fixa" : "Variável";
    }
    
    @Override
    public String toString() {
        return String.format("Despesa: %s | Valor: R$ %.2f | Data: %s | Tipo: %s | Categoria: %s",
                descricao, valor, getDataFormatada(), getTipoDespesa(), categoria.getNome());
    }
    
    @Override
    public String toFileString() {
        return String.format("DESPESA|%.2f|%s|%s|%d|%s",
                valor, getDataFormatada(), descricao, categoria.getId(), fixa ? "FIXA" : "VARIAVEL");
    }
}