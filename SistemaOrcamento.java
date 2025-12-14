package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class SistemaOrcamento {
    private List<Usuario> usuarios;
    private List<Categoria> categorias;
    private List<Receita> receitas;
    private List<Despesa> despesas;
    private Usuario usuarioAtual;
    
    public SistemaOrcamento() {
        this.usuarios = new ArrayList<>();
        this.categorias = new ArrayList<>();
        this.receitas = new ArrayList<>();
        this.despesas = new ArrayList<>();
        this.usuarioAtual = null;
        carregarCategoriasPadrao();
    }
    
    private void carregarCategoriasPadrao() {
        categorias.add(new Categoria("Salário", "RECEITA"));
        categorias.add(new Categoria("Freelance", "RECEITA"));
        categorias.add(new Categoria("Investimentos", "RECEITA"));
        categorias.add(new Categoria("Outras Receitas", "RECEITA"));
        
        categorias.add(new Categoria("Moradia", "DESPESA"));
        categorias.add(new Categoria("Alimentação", "DESPESA"));
        categorias.add(new Categoria("Transporte", "DESPESA"));
        categorias.add(new Categoria("Saúde", "DESPESA"));
        categorias.add(new Categoria("Educação", "DESPESA"));
        categorias.add(new Categoria("Lazer", "DESPESA"));
        categorias.add(new Categoria("Outras Despesas", "DESPESA"));
    }
    
    // Métodos para Usuários
    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        if (usuarioAtual == null) {
            usuarioAtual = usuario;
        }
    }
    
    public boolean removerUsuario(int id) {
        return usuarios.removeIf(u -> u.getId() == id);
    }
    
    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }
    
    public Usuario buscarUsuario(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }
    
    public void setUsuarioAtual(Usuario usuario) {
        this.usuarioAtual = usuario;
    }
    
    public Usuario getUsuarioAtual() {
        return usuarioAtual;
    }
    
    // Métodos para Categorias
    public void adicionarCategoria(Categoria categoria) {
        categorias.add(categoria);
    }
    
    public boolean removerCategoria(int id) {
        return categorias.removeIf(c -> c.getId() == id);
    }
    
    public List<Categoria> getCategorias() {
        return new ArrayList<>(categorias);
    }
    
    public List<Categoria> getCategoriasPorTipo(String tipo) {
        List<Categoria> resultado = new ArrayList<>();
        for (Categoria c : categorias) {
            if (c.getTipo().equalsIgnoreCase(tipo)) {
                resultado.add(c);
            }
        }
        return resultado;
    }
    
    public Categoria buscarCategoria(int id) {
        for (Categoria c : categorias) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
    
    // Métodos para Receitas
    public void adicionarReceita(Receita receita) {
        receitas.add(receita);
    }
    
    public boolean removerReceita(int index) {
        if (index >= 0 && index < receitas.size()) {
            receitas.remove(index);
            return true;
        }
        return false;
    }
    
    public List<Receita> getReceitas() {
        return new ArrayList<>(receitas);
    }
    
    // Métodos para Despesas
    public void adicionarDespesa(Despesa despesa) {
        despesas.add(despesa);
    }
    
    public boolean removerDespesa(int index) {
        if (index >= 0 && index < despesas.size()) {
            despesas.remove(index);
            return true;
        }
        return false;
    }
    
    public List<Despesa> getDespesas() {
        return new ArrayList<>(despesas);
    }
    
    // Métodos de cálculo
    public double calcularTotalReceitas() {
        double total = 0;
        for (Receita r : receitas) {
            total += r.getValor();
        }
        return total;
    }
    
    public double calcularTotalDespesas() {
        double total = 0;
        for (Despesa d : despesas) {
            total += d.getValor();
        }
        return total;
    }
    
    public double calcularSaldoAtual() {
        if (usuarioAtual == null) return 0;
        return usuarioAtual.getSaldoInicial() + calcularTotalReceitas() - calcularTotalDespesas();
    }
    
    // Métodos para análise
    public void gerarRelatorio() {
        System.out.println("\n=== RELATÓRIO FINANCEIRO ===");
        System.out.printf("Usuário: %s\n", usuarioAtual != null ? usuarioAtual.getNome() : "Nenhum");
        System.out.printf("Saldo Inicial: R$ %.2f\n", usuarioAtual != null ? usuarioAtual.getSaldoInicial() : 0);
        System.out.printf("Total de Receitas: R$ %.2f\n", calcularTotalReceitas());
        System.out.printf("Total de Despesas: R$ %.2f\n", calcularTotalDespesas());
        System.out.printf("Saldo Atual: R$ %.2f\n", calcularSaldoAtual());
        
        System.out.println("\nReceitas por Categoria:");
        for (Categoria c : getCategoriasPorTipo("RECEITA")) {
            double total = 0;
            for (Receita r : receitas) {
                if (r.getCategoria().getId() == c.getId()) {
                    total += r.getValor();
                }
            }
            if (total > 0) {
                System.out.printf("  %s: R$ %.2f\n", c.getNome(), total);
            }
        }
        
        System.out.println("\nDespesas por Categoria:");
        for (Categoria c : getCategoriasPorTipo("DESPESA")) {
            double total = 0;
            for (Despesa d : despesas) {
                if (d.getCategoria().getId() == c.getId()) {
                    total += d.getValor();
                }
            }
            if (total > 0) {
                System.out.printf("  %s: R$ %.2f\n", c.getNome(), total);
            }
        }
    }
    
    // Parse de data
    public static Date parseData(String dataStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(dataStr);
    }
}