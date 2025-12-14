import modelo.*;
import persistencia.GerenciadorArquivos;
import java.util.Scanner;
import java.util.List;
import java.util.Date;

public class Main {
    private static SistemaOrcamento sistema = new SistemaOrcamento();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE CONTROLE DE ORÇAMENTO PESSOAL ===");
        carregarDados();
        exibirMenuPrincipal();
    }
    
    private static void carregarDados() {
        System.out.println("\nCarregando dados do sistema...");
        GerenciadorArquivos.carregarTudo(sistema);
    }
    
    private static void exibirMenuPrincipal() {
        int opcao;
        
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("Usuário atual: " + 
                (sistema.getUsuarioAtual() != null ? sistema.getUsuarioAtual().getNome() : "Nenhum"));
            System.out.println("Saldo atual: R$ " + String.format("%.2f", sistema.calcularSaldoAtual()));
            System.out.println("\n1. Gerenciar Usuários");
            System.out.println("2. Gerenciar Categorias");
            System.out.println("3. Adicionar Receita");
            System.out.println("4. Adicionar Despesa");
            System.out.println("5. Listar Movimentações");
            System.out.println("6. Relatórios e Análises");
            System.out.println("7. Importar/Exportar Dados");
            System.out.println("8. Salvar e Sair");
            System.out.println("0. Sair sem salvar");
            System.out.print("\nEscolha uma opção: ");
            
            opcao = lerInteiro();
            
            switch (opcao) {
                case 1: menuUsuarios(); break;
                case 2: menuCategorias(); break;
                case 3: adicionarReceita(); break;
                case 4: adicionarDespesa(); break;
                case 5: menuListagens(); break;
                case 6: menuRelatorios(); break;
                case 7: menuImportarExportar(); break;
                case 8: salvarESair(); break;
                case 0: sairSemSalvar(); break;
                default: System.out.println("Opção inválida!");
            }
            
        } while (opcao != 8 && opcao != 0);
    }
    
    private static void menuUsuarios() {
        int opcao;
        
        do {
            System.out.println("\n=== GERENCIAR USUÁRIOS ===");
            System.out.println("1. Adicionar Usuário");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Remover Usuário");
            System.out.println("4. Selecionar Usuário Atual");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            opcao = lerInteiro();
            
            switch (opcao) {
                case 1: adicionarUsuario(); break;
                case 2: listarUsuarios(); break;
                case 3: removerUsuario(); break;
                case 4: selecionarUsuario(); break;
                case 5: break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 5);
    }
    
    private static void adicionarUsuario() {
        System.out.println("\n=== ADICIONAR USUÁRIO ===");
        
        System.out.print("Nome: ");
        scanner.nextLine();
        String nome = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Saldo Inicial: R$ ");
        double saldo = lerDouble();
        
        Usuario usuario = new Usuario(nome, email, saldo);
        sistema.adicionarUsuario(usuario);
        
        System.out.println("Usuário adicionado com sucesso!");
        sistema.setUsuarioAtual(usuario);
    }
    
    private static void listarUsuarios() {
        List<Usuario> usuarios = sistema.getUsuarios();
        
        if (usuarios.isEmpty()) {
            System.out.println("\nNenhum usuário cadastrado.");
        } else {
            System.out.println("\n=== LISTA DE USUÁRIOS ===");
            for (Usuario usuario : usuarios) {
                System.out.println(usuario);
            }
        }
    }
    
    private static void removerUsuario() {
        listarUsuarios();
        
        if (sistema.getUsuarios().isEmpty()) {
            return;
        }
        
        System.out.print("\nID do usuário a remover: ");
        int id = lerInteiro();
        
        if (sistema.removerUsuario(id)) {
            System.out.println("Usuário removido com sucesso!");
        } else {
            System.out.println("Usuário não encontrado!");
        }
    }
    
    private static void selecionarUsuario() {
        listarUsuarios();
        
        if (sistema.getUsuarios().isEmpty()) {
            return;
        }
        
        System.out.print("\nID do usuário para selecionar: ");
        int id = lerInteiro();
        
        Usuario usuario = sistema.buscarUsuario(id);
        if (usuario != null) {
            sistema.setUsuarioAtual(usuario);
            System.out.println("Usuário " + usuario.getNome() + " selecionado como atual!");
        } else {
            System.out.println("Usuário não encontrado!");
        }
    }
    
    private static void menuCategorias() {
        int opcao;
        
        do {
            System.out.println("\n=== GERENCIAR CATEGORIAS ===");
            System.out.println("1. Adicionar Categoria");
            System.out.println("2. Listar Categorias");
            System.out.println("3. Remover Categoria");
            System.out.println("4. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            opcao = lerInteiro();
            
            switch (opcao) {
                case 1: adicionarCategoria(); break;
                case 2: listarCategorias(); break;
                case 3: removerCategoria(); break;
                case 4: break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 4);
    }
    
    private static void adicionarCategoria() {
        System.out.println("\n=== ADICIONAR CATEGORIA ===");
        
        System.out.print("Nome da categoria: ");
        scanner.nextLine();
        String nome = scanner.nextLine();
        
        System.out.print("Tipo (RECEITA/DESPESA): ");
        String tipo = scanner.nextLine().toUpperCase();
        
        if (tipo.equals("RECEITA") || tipo.equals("DESPESA")) {
            Categoria categoria = new Categoria(nome, tipo);
            sistema.adicionarCategoria(categoria);
            System.out.println("Categoria adicionada com sucesso!");
        } else {
            System.out.println("Tipo inválido! Use RECEITA ou DESPESA.");
        }
    }
    
    private static void listarCategorias() {
        List<Categoria> categorias = sistema.getCategorias();
        
        if (categorias.isEmpty()) {
            System.out.println("\nNenhuma categoria cadastrada.");
        } else {
            System.out.println("\n=== LISTA DE CATEGORIAS ===");
            
            System.out.println("\nCategorias de Receita:");
            List<Categoria> categoriasReceita = sistema.getCategoriasPorTipo("RECEITA");
            if (categoriasReceita.isEmpty()) {
                System.out.println("  Nenhuma categoria de receita cadastrada.");
            } else {
                for (Categoria categoria : categoriasReceita) {
                    System.out.println("  " + categoria);
                }
            }
            
            System.out.println("\nCategorias de Despesa:");
            List<Categoria> categoriasDespesa = sistema.getCategoriasPorTipo("DESPESA");
            if (categoriasDespesa.isEmpty()) {
                System.out.println("  Nenhuma categoria de despesa cadastrada.");
            } else {
                for (Categoria categoria : categoriasDespesa) {
                    System.out.println("  " + categoria);
                }
            }
        }
    }
    
    private static void removerCategoria() {
        listarCategorias();
        
        if (sistema.getCategorias().isEmpty()) {
            return;
        }
        
        System.out.print("\nID da categoria a remover: ");
        int id = lerInteiro();
        
        if (sistema.removerCategoria(id)) {
            System.out.println("Categoria removida com sucesso!");
        } else {
            System.out.println("Categoria não encontrada!");
        }
    }
    
    private static void adicionarReceita() {
        if (sistema.getUsuarioAtual() == null) {
            System.out.println("Selecione um usuário primeiro!");
            return;
        }
        
        System.out.println("\n=== ADICIONAR RECEITA ===");
        
        try {
            scanner.nextLine(); // Limpar buffer
            
            System.out.print("Descrição: ");
            String descricao = scanner.nextLine();
            
            System.out.print("Valor: R$ ");
            double valor = lerDouble();
            
            System.out.print("Data (dd/MM/yyyy): ");
            String dataStr = scanner.nextLine();
            Date data = SistemaOrcamento.parseData(dataStr);
            
            System.out.print("Fonte (ex: Salário, Freelance): ");
            String fonte = scanner.nextLine();
            
            // Listar categorias de receita
            List<Categoria> categoriasReceita = sistema.getCategoriasPorTipo("RECEITA");
            System.out.println("\nCategorias disponíveis:");
            for (Categoria categoria : categoriasReceita) {
                System.out.println("  " + categoria);
            }
            
            System.out.print("ID da categoria: ");
            int idCategoria = lerInteiro();
            
            Categoria categoria = sistema.buscarCategoria(idCategoria);
            if (categoria != null && categoria.getTipo().equals("RECEITA")) {
                Receita receita = new Receita(valor, data, descricao, categoria, fonte);
                sistema.adicionarReceita(receita);
                System.out.println("Receita adicionada com sucesso!");
            } else {
                System.out.println("Categoria inválida ou não é do tipo RECEITA!");
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao adicionar receita: " + e.getMessage());
        }
    }
    
    private static void adicionarDespesa() {
        if (sistema.getUsuarioAtual() == null) {
            System.out.println("Selecione um usuário primeiro!");
            return;
        }
        
        System.out.println("\n=== ADICIONAR DESPESA ===");
        
        try {
            scanner.nextLine(); // Limpar buffer
            
            System.out.print("Descrição: ");
            String descricao = scanner.nextLine();
            
            System.out.print("Valor: R$ ");
            double valor = lerDouble();
            
            System.out.print("Data (dd/MM/yyyy): ");
            String dataStr = scanner.nextLine();
            Date data = SistemaOrcamento.parseData(dataStr);
            
            System.out.print("É despesa fixa? (S/N): ");
            String fixaStr = scanner.nextLine();
            boolean fixa = fixaStr.equalsIgnoreCase("S");
            
            // Listar categorias de despesa
            List<Categoria> categoriasDespesa = sistema.getCategoriasPorTipo("DESPESA");
            System.out.println("\nCategorias disponíveis:");
            for (Categoria categoria : categoriasDespesa) {
                System.out.println("  " + categoria);
            }
            
            System.out.print("ID da categoria: ");
            int idCategoria = lerInteiro();
            
            Categoria categoria = sistema.buscarCategoria(idCategoria);
            if (categoria != null && categoria.getTipo().equals("DESPESA")) {
                Despesa despesa = new Despesa(valor, data, descricao, categoria, fixa);
                sistema.adicionarDespesa(despesa);
                System.out.println("Despesa adicionada com sucesso!");
            } else {
                System.out.println("Categoria inválida ou não é do tipo DESPESA!");
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao adicionar despesa: " + e.getMessage());
        }
    }
    
    private static void menuListagens() {
        int opcao;
        
        do {
            System.out.println("\n=== LISTAGENS ===");
            System.out.println("1. Listar Todas as Receitas");
            System.out.println("2. Listar Todas as Despesas");
            System.out.println("3. Listar Todas as Movimentações");
            System.out.println("4. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            opcao = lerInteiro();
            
            switch (opcao) {
                case 1: listarReceitas(); break;
                case 2: listarDespesas(); break;
                case 3: 
                    listarReceitas();
                    listarDespesas();
                    break;
                case 4: break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 4);
    }
    
    private static void listarReceitas() {
        List<Receita> receitas = sistema.getReceitas();
        
        if (receitas.isEmpty()) {
            System.out.println("\nNenhuma receita cadastrada.");
        } else {
            System.out.println("\n=== LISTA DE RECEITAS ===");
            double total = 0;
            
            for (int i = 0; i < receitas.size(); i++) {
                Receita receita = receitas.get(i);
                System.out.println((i + 1) + ". " + receita);
                total += receita.getValor();
            }
            
            System.out.printf("\nTotal de Receitas: R$ %.2f\n", total);
        }
    }
    
    private static void listarDespesas() {
        List<Despesa> despesas = sistema.getDespesas();
        
        if (despesas.isEmpty()) {
            System.out.println("\nNenhuma despesa cadastrada.");
        } else {
            System.out.println("\n=== LISTA DE DESPESAS ===");
            double total = 0;
            
            for (int i = 0; i < despesas.size(); i++) {
                Despesa despesa = despesas.get(i);
                System.out.println((i + 1) + ". " + despesa);
                total += despesa.getValor();
            }
            
            System.out.printf("\nTotal de Despesas: R$ %.2f\n", total);
        }
    }
    
    private static void menuRelatorios() {
        int opcao;
        
        do {
            System.out.println("\n=== RELATÓRIOS E ANÁLISES ===");
            System.out.println("1. Relatório Financeiro Completo");
            System.out.println("2. Saldo Atual");
            System.out.println("3. Comparativo Receitas vs Despesas");
            System.out.println("4. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            opcao = lerInteiro();
            
            switch (opcao) {
                case 1: 
                    sistema.gerarRelatorio();
                    break;
                case 2:
                    System.out.printf("\nSaldo Atual: R$ %.2f\n", sistema.calcularSaldoAtual());
                    break;
                case 3:
                    double receitas = sistema.calcularTotalReceitas();
                    double despesas = sistema.calcularTotalDespesas();
                    System.out.println("\n=== COMPARATIVO ===");
                    System.out.printf("Total Receitas: R$ %.2f\n", receitas);
                    System.out.printf("Total Despesas: R$ %.2f\n", despesas);
                    System.out.printf("Diferença: R$ %.2f\n", receitas - despesas);
                    
                    if (receitas > despesas) {
                        System.out.println("Situação: SUPERÁVIT (Receitas > Despesas)");
                    } else if (receitas < despesas) {
                        System.out.println("Situação: DÉFICIT (Receitas < Despesas)");
                    } else {
                        System.out.println("Situação: EQUILÍBRIO (Receitas = Despesas)");
                    }
                    break;
                case 4: break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 4);
    }
    
    private static void menuImportarExportar() {
        int opcao;
        
        do {
            System.out.println("\n=== IMPORTAR/EXPORTAR DADOS ===");
            System.out.println("1. Exportar Dados para CSV");
            System.out.println("2. Importar Dados de CSV");
            System.out.println("3. Salvar Dados Atuais");
            System.out.println("4. Carregar Dados Salvos");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            opcao = lerInteiro();
            
            switch (opcao) {
                case 1:
                    GerenciadorArquivos.exportarParaCSV(sistema);
                    break;
                case 2:
                    System.out.print("Nome do arquivo CSV para importar: ");
                    scanner.nextLine();
                    String nomeArquivo = scanner.nextLine();
                    GerenciadorArquivos.importarDeCSV(sistema, nomeArquivo);
                    break;
                case 3:
                    GerenciadorArquivos.salvarTudo(sistema);
                    break;
                case 4:
                    carregarDados();
                    break;
                case 5: break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 5);
    }
    
    private static void salvarESair() {
        System.out.println("\nSalvando dados...");
        GerenciadorArquivos.salvarTudo(sistema);
        System.out.println("Dados salvos com sucesso!");
        System.out.println("Até logo!");
        scanner.close();
    }
    
    private static void sairSemSalvar() {
        System.out.println("\nSaindo sem salvar...");
        System.out.println("Todos os dados não salvos serão perdidos!");
        System.out.print("Tem certeza? (S/N): ");
        scanner.nextLine();
        String confirmacao = scanner.nextLine();
        
        if (confirmacao.equalsIgnoreCase("S")) {
            System.out.println("Até logo!");
            scanner.close();
            System.exit(0);
        } else {
            System.out.println("Operação cancelada.");
        }
    }
    
    // Métodos auxiliares
    private static int lerInteiro() {
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor, digite um número inteiro válido: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
    
    private static double lerDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Por favor, digite um número decimal válido: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}