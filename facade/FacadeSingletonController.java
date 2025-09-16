package facade;

import controle.GerenciamentoDocumento;
import controle.GerenciamentoUsuario;
import entidade.Documento;
import entidade.Usuario;
import infra.DocumentoRepositorio;
import infra.UsuarioRepositorio;
import java.util.List;
import utils.ExcecoesLogin;
import utils.ExcecoesRepositorio;
import utils.ExcecoesSenha;

public class FacadeSingletonController {
    // Instância única do Singleton
    private static FacadeSingletonController instance;

    //Controladores
    private GerenciamentoUsuario gerenciamentoUsuario;
    private GerenciamentoDocumento gerenciamentoDocumento;

    // Construtor privado para impedir instanciação externa
    private FacadeSingletonController(UsuarioRepositorio usuarioRepositorio, 
                                     DocumentoRepositorio documentoRepositorio) {
        this.gerenciamentoUsuario = new GerenciamentoUsuario(usuarioRepositorio);
        this.gerenciamentoDocumento = new GerenciamentoDocumento(documentoRepositorio, usuarioRepositorio);
    }

    // Método para obter a instância única (Singleton)
    public static synchronized FacadeSingletonController getInstance(UsuarioRepositorio usuarioRepositorio, 
                                                                   DocumentoRepositorio documentoRepositorio) {
        if (instance == null) {
            instance = new FacadeSingletonController(usuarioRepositorio, documentoRepositorio);
        }
        return instance;
    }
    
    // Método para obter a instância sem parâmetros (após inicialização)
    public static synchronized FacadeSingletonController getInstance() {
        if (instance == null) {
            throw new IllegalStateException("FacadeSingletonController não foi inicializado. "
                                         + "Use getInstance(UsuarioRepositorio, DocumentoRepositorio) primeiro.");
        }
        return instance;
    }

    // ---- Métodos relacionados a usuário ----
    public void cadastrarUsuario(String login, String senha) 
            throws ExcecoesRepositorio, ExcecoesLogin, ExcecoesSenha {
        gerenciamentoUsuario.adicionarUsuario(login, senha);
    }

    public List<Usuario> listarUsuarios() throws ExcecoesRepositorio {
        return gerenciamentoUsuario.listarUsuarios();
    }

    public Usuario buscarUsuario(String login) throws ExcecoesRepositorio {
        return gerenciamentoUsuario.buscarUsuario(login);
    }

    // ---- Métodos relacionados a documentos ----
    public void cadastrarDocumento(String nome, int tamanho, String usuarioAssociado) 
            throws ExcecoesRepositorio {
        gerenciamentoDocumento.adicionarDocumento(nome, tamanho, usuarioAssociado);
    }

    public void removerDocumento(String nome) throws ExcecoesRepositorio {
        gerenciamentoDocumento.removerDocumento(nome);
    }

    public List<Documento> listarDocumentos() throws ExcecoesRepositorio {
        return gerenciamentoDocumento.listarDocumentos();
    }

    public Documento buscarDocumentoPorNome(String nome) throws ExcecoesRepositorio {
        return gerenciamentoDocumento.buscarDocumentoPorNome(nome);
    }

    public boolean verificarUsuario(String login, String senha) throws ExcecoesRepositorio {
        // Verificar se é admin
        if (login.equals("admin") && senha.equals("admin")) {
            return true;
        }
        
        // Verificar se é um usuário cadastrado
        Usuario usuario = buscarUsuario(login);
        return usuario != null && usuario.getSenha().equals(senha);
    }

    // Método para obter a quantidade total de entidades cadastradas
    public int getQuantidadeEntidades() throws ExcecoesRepositorio {
        int quantidadeUsuarios = gerenciamentoUsuario.listarUsuarios().size();
        int quantidadeDocumentos = gerenciamentoDocumento.listarDocumentos().size();
        return quantidadeUsuarios + quantidadeDocumentos;
    }

    // Método para obter estatísticas detalhadas
    public String getEstatisticas() throws ExcecoesRepositorio {
        int quantidadeUsuarios = gerenciamentoUsuario.listarUsuarios().size();
        int quantidadeDocumentos = gerenciamentoDocumento.listarDocumentos().size();
        
        return String.format("Estatísticas do Sistema:%n" +
                           "- Usuários cadastrados: %d%n" +
                           "- Documentos cadastrados: %d%n" +
                           "- Total de entidades: %d", 
                           quantidadeUsuarios, quantidadeDocumentos, 
                           quantidadeUsuarios + quantidadeDocumentos);
    }

    // Método para resetar a instância (útil para testes)
    public static synchronized void resetInstance() {
        instance = null;
    }
}
