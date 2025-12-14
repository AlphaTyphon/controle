package modelo;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private double saldoInicial;
    private static int proximoId = 1;
    
    public Usuario(String nome, String email, double saldoInicial) {
        this.id = proximoId++;
        this.nome = nome;
        this.email = email;
        this.saldoInicial = saldoInicial;
    }
    
    public Usuario(int id, String nome, String email, double saldoInicial) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.saldoInicial = saldoInicial;
        if (id >= proximoId) {
            proximoId = id + 1;
        }
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public double getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(double saldoInicial) { this.saldoInicial = saldoInicial; }
    
    @Override
    public String toString() {
        return String.format("[%d] %s - %s | Saldo Inicial: R$ %.2f", 
                id, nome, email, saldoInicial);
    }
    
    public String toFileString() {
        return String.format("%d|%s|%s|%.2f", id, nome, email, saldoInicial);
    }
}