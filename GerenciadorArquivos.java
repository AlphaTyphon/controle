package persistencia;

import modelo.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class GerenciadorArquivos {
    private static final String PASTA_DADOS = "dados/";
    
    // Salvar usuários em arquivo texto
    public static void salvarUsuarios(List<Usuario> usuarios) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PASTA_DADOS + "usuarios.txt"))) {
            for (Usuario usuario : usuarios) {
                writer.println(usuario.toFileString());
            }
            System.out.println("Usuários salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }
    
    // Carregar usuários de arquivo texto
    public static List<Usuario> carregarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        File arquivo = new File(PASTA_DADOS + "usuarios.txt");
        
        if (!arquivo.exists()) {
            System.out.println("Arquivo de usuários não encontrado. Criando novo...");
            return usuarios;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split("\\|");
                if (partes.length == 4) {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    String email = partes[2];
                    double saldo = Double.parseDouble(partes[3]);
                    usuarios.add(new Usuario(id, nome, email, saldo));
                }
            }
            System.out.println("Usuários carregados: " + usuarios.size());
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro no formato do arquivo de usuários: " + e.getMessage());
        }
        
        return usuarios;
    }
    
    // Salvar categorias em arquivo texto
    public static void salvarCategorias(List<Categoria> categorias) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PASTA_DADOS + "categorias.txt"))) {
            for (Categoria categoria : categorias) {
                writer.println(categoria.toFileString());
            }
            System.out.println("Categorias salvas com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar categorias: " + e.getMessage());
        }
    }
    
    // Carregar categorias de arquivo texto
    public static List<Categoria> carregarCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        File arquivo = new File(PASTA_DADOS + "categorias.txt");
        
        if (!arquivo.exists()) {
            System.out.println("Arquivo de categorias não encontrado.");
            return categorias;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split("\\|");
                if (partes.length == 3) {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    String tipo = partes[2];
                    categorias.add(new Categoria(id, nome, tipo));
                }
            }
            System.out.println("Categorias carregadas: " + categorias.size());
        } catch (IOException e) {
            System.out.println("Erro ao carregar categorias: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro no formato do arquivo de categorias: " + e.getMessage());
        }
        
        return categorias;
    }
    
    // Salvar receitas em arquivo texto
    public static void salvarReceitas(List<Receita> receitas, List<Categoria> categorias) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PASTA_DADOS + "receitas.txt"))) {
            for (Receita receita : receitas) {
                writer.println(receita.toFileString());
            }
            System.out.println("Receitas salvas com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar receitas: " + e.getMessage());
        }
    }
    
    // Carregar receitas de arquivo texto
    public static List<Receita> carregarReceitas(List<Categoria> categorias) {
        List<Receita> receitas = new ArrayList<>();
        File arquivo = new File(PASTA_DADOS + "receitas.txt");
        
        if (!arquivo.exists()) {
            System.out.println("Arquivo de receitas não encontrado.");
            return receitas;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split("\\|");
                if (partes.length == 6 && partes[0].equals("RECEITA")) {
                    double valor = Double.parseDouble(partes[1]);
                    Date data = sdf.parse(partes[2]);
                    String descricao = partes[3];
                    int categoriaId = Integer.parseInt(partes[4]);
                    String fonte = partes[5];
                    
                    Categoria categoria = buscarCategoriaPorId(categorias, categoriaId);
                    if (categoria != null) {
                        receitas.add(new Receita(valor, data, descricao, categoria, fonte));
                    }
                }
            }
            System.out.println("Receitas carregadas: " + receitas.size());
        } catch (IOException | ParseException e) {
            System.out.println("Erro ao carregar receitas: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro no formato do arquivo de receitas: " + e.getMessage());
        }
        
        return receitas;
    }
    
    // Salvar despesas em arquivo texto
    public static void salvarDespesas(List<Despesa> despesas, List<Categoria> categorias) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PASTA_DADOS + "despesas.txt"))) {
            for (Despesa despesa : despesas) {
                writer.println(despesa.toFileString());
            }
            System.out.println("Despesas salvas com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar despesas: " + e.getMessage());
        }
    }
    
    // Carregar despesas de arquivo texto
    public static List<Despesa> carregarDespesas(List<Categoria> categorias) {
        List<Despesa> despesas = new ArrayList<>();
        File arquivo = new File(PASTA_DADOS + "despesas.txt");
        
        if (!arquivo.exists()) {
            System.out.println("Arquivo de despesas não encontrado.");
            return despesas;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split("\\|");
                if (partes.length == 6 && partes[0].equals("DESPESA")) {
                    double valor = Double.parseDouble(partes[1]);
                    Date data = sdf.parse(partes[2]);
                    String descricao = partes[3];
                    int categoriaId = Integer.parseInt(partes[4]);
                    boolean fixa = partes[5].equals("FIXA");
                    
                    Categoria categoria = buscarCategoriaPorId(categorias, categoriaId);
                    if (categoria != null) {
                        despesas.add(new Despesa(valor, data, descricao, categoria, fixa));
                    }
                }
            }
            System.out.println("Despesas carregadas: " + despesas.size());
        } catch (IOException | ParseException e) {
            System.out.println("Erro ao carregar despesas: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro no formato do arquivo de despesas: " + e.getMessage());
        }
        
        return despesas;
    }
    
    // Método auxiliar para buscar categoria por ID
    private static Categoria buscarCategoriaPorId(List<Categoria> categorias, int id) {
        for (Categoria c : categorias) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
    
    // Salvar todos os dados
    public static void salvarTudo(SistemaOrcamento sistema) {
        criarPastaDados();
        salvarUsuarios(sistema.getUsuarios());
        salvarCategorias(sistema.getCategorias());
        salvarReceitas(sistema.getReceitas(), sistema.getCategorias());
        salvarDespesas(sistema.getDespesas(), sistema.getCategorias());
    }
    
    // Carregar todos os dados
    public static void carregarTudo(SistemaOrcamento sistema) {
        criarPastaDados();
        
        // Carregar categorias primeiro (necessárias para receitas/despesas)
        List<Categoria> categoriasCarregadas = carregarCategorias();
        for (Categoria c : categoriasCarregadas) {
            sistema.adicionarCategoria(c);
        }
        
        // Carregar usuários
        List<Usuario> usuariosCarregados = carregarUsuarios();
        for (Usuario u : usuariosCarregados) {
            sistema.adicionarUsuario(u);
        }
        
        // Carregar receitas e despesas
        List<Receita> receitasCarregadas = carregarReceitas(sistema.getCategorias());
        for (Receita r : receitasCarregadas) {
            sistema.adicionarReceita(r);
        }
        
        List<Despesa> despesasCarregadas = carregarDespesas(sistema.getCategorias());
        for (Despesa d : despesasCarregadas) {
            sistema.adicionarDespesa(d);
        }
    }
    
    // Criar pasta de dados se não existir
    private static void criarPastaDados() {
        File pasta = new File(PASTA_DADOS);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
    }
    
    // Exportar para CSV
    public static void exportarParaCSV(SistemaOrcamento sistema) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PASTA_DADOS + "exportacao.csv"))) {
            // Cabeçalho
            writer.println("Tipo;Descrição;Valor;Data;Categoria;Detalhe");
            
            // Receitas
            for (Receita r : sistema.getReceitas()) {
                writer.println(String.format("RECEITA;%s;%.2f;%s;%s;%s",
                    r.getDescricao(),
                    r.getValor(),
                    r.getDataFormatada(),
                    r.getCategoria().getNome(),
                    r.getFonte()
                ));
            }
            
            // Despesas
            for (Despesa d : sistema.getDespesas()) {
                writer.println(String.format("DESPESA;%s;%.2f;%s;%s;%s",
                    d.getDescricao(),
                    d.getValor(),
                    d.getDataFormatada(),
                    d.getCategoria().getNome(),
                    d.getTipoDespesa()
                ));
            }
            
            System.out.println("Dados exportados para exportacao.csv");
        } catch (IOException e) {
            System.out.println("Erro ao exportar CSV: " + e.getMessage());
        }
    }
    
    // Importar de CSV (implementação básica)
    public static void importarDeCSV(SistemaOrcamento sistema, String nomeArquivo) {
        File arquivo = new File(PASTA_DADOS + nomeArquivo);
        
        if (!arquivo.exists()) {
            System.out.println("Arquivo " + nomeArquivo + " não encontrado!");
            return;
        }
        
        int importados = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeiraLinha = true;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue; // Pular cabeçalho
                }
                
                String[] partes = linha.split(";");
                if (partes.length >= 6) {
                    String tipo = partes[0];
                    String descricao = partes[1];
                    double valor = Double.parseDouble(partes[2]);
                    Date data = sdf.parse(partes[3]);
                    String nomeCategoria = partes[4];
                    String detalhe = partes[5];
                    
                    // Buscar categoria pelo nome
                    Categoria categoria = null;
                    for (Categoria c : sistema.getCategorias()) {
                        if (c.getNome().equalsIgnoreCase(nomeCategoria)) {
                            categoria = c;
                            break;
                        }
                    }
                    
                    if (categoria == null) {
                        // Criar nova categoria se não existir
                        categoria = new Categoria(nomeCategoria, tipo);
                        sistema.adicionarCategoria(categoria);
                    }
                    
                    if (tipo.equals("RECEITA")) {
                        Receita receita = new Receita(valor, data, descricao, categoria, detalhe);
                        sistema.adicionarReceita(receita);
                        importados++;
                    } else if (tipo.equals("DESPESA")) {
                        boolean fixa = detalhe.equals("Fixa");
                        Despesa despesa = new Despesa(valor, data, descricao, categoria, fixa);
                        sistema.adicionarDespesa(despesa);
                        importados++;
                    }
                }
            }
            
            System.out.println("Importados " + importados + " registros do arquivo " + nomeArquivo);
        } catch (IOException | ParseException e) {
            System.out.println("Erro ao importar CSV: " + e.getMessage());
        }
    }
}