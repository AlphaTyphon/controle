package modelo;

public class Categoria {
    private int id;
    private String nome;
    private String tipo;
    private static int proximoId = 1;
    
    public Categoria(String nome, String tipo) {
        this.id = proximoId++;
        this.nome = nome;
        this.tipo = tipo.toUpperCase();
    }
    
    public Categoria(int id, String nome, String tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo.toUpperCase();
        if (id >= proximoId) {
            proximoId = id + 1;
        }
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    @Override
    public String toString() {
        return String.format("[%d] %s (%s)", id, nome, tipo);
    }
    
    public String toFileString() {
        return String.format("%d|%s|%s", id, nome, tipo);
    }
}