package facade;

import controle.GerenciamentoDocumento;
import controle.GerenciamentoUsuario;
import controle.RelatorioHTML;
import controle.RelatorioPDF;
import controle.RelatorioTemplate;
import entidade.Documento;
import entidade.Usuario;
import infra.DocumentoRepositorio;
import infra.RepositorioFactory;
import infra.UsuarioRepositorio;
import java.util.List;
import memento.DocumentoMemento;
import memento.MementoCaretaker;
import memento.UsuarioMemento;
import utils.ExcecoesLogin;
import utils.ExcecoesRepositorio;
import utils.ExcecoesSenha;

public class FacadeSingletonController {
    // Instância única do Singleton
    private static FacadeSingletonController instance;

    // Controladores
    private GerenciamentoUsuario gerenciamentoUsuario;
    private GerenciamentoDocumento gerenciamentoDocumento;
    
    // Memento Caretaker para gerenciar estados salvos
    private MementoCaretaker mementoCaretaker;

    // Construtor privado - AGORA SÓ EXISTE UM
    /*private FacadeSingletonController(RepositorioFactory factory) {
        // A fachada usa a fábrica para criar os repositórios, sem conhecer as classes
        // concretas
        UsuarioRepositorio usuarioRepositorio = factory.createUsuarioRepositorio();
        DocumentoRepositorio documentoRepositorio = factory.createDocumentoRepositorio();
    }
    */
    // Construtor privado - AGORA SÓ EXISTE UM
    private FacadeSingletonController(RepositorioFactory factory) {
        // A fachada usa a fábrica para criar os repositórios, sem conhecer as classes
        // concretas
        UsuarioRepositorio usuarioRepositorio = factory.createUsuarioRepositorio();
        DocumentoRepositorio documentoRepositorio = factory.createDocumentoRepositorio();

        this.gerenciamentoUsuario = new GerenciamentoUsuario(usuarioRepositorio);
        this.gerenciamentoDocumento = new GerenciamentoDocumento(documentoRepositorio, usuarioRepositorio);
        this.mementoCaretaker = new MementoCaretaker();
    }

    // Método para obter a instância única (Singleton) - AGORA SÓ EXISTE UM
    public static synchronized FacadeSingletonController getInstance(RepositorioFactory factory) {
        if (instance == null) {
            instance = new FacadeSingletonController(factory);
        }
        return instance;
    }

    // ---- Métodos relacionados a usuário ----
    public void cadastrarUsuario(String login, String senha)
            throws ExcecoesRepositorio, ExcecoesLogin, ExcecoesSenha {
        gerenciamentoUsuario.adicionarUsuario(login, senha);
    }

    public void removerUsuario(String login) throws ExcecoesRepositorio {
        gerenciamentoUsuario.removerUsuario(login);
    }

    public List<Usuario> listarUsuarios() throws ExcecoesRepositorio {
        return gerenciamentoUsuario.listarUsuarios();
    }

    public void atualizarUsuario(Usuario usuario) throws ExcecoesRepositorio {
        gerenciamentoUsuario.atualizarUsuario(usuario);
    }

    public Usuario buscarUsuario(String login) throws ExcecoesRepositorio {
        return gerenciamentoUsuario.buscarUsuario(login);
    }

    public boolean verificarUsuario(String login, String senha) throws ExcecoesRepositorio {
        Usuario usuario = buscarUsuario(login);
        return usuario != null && usuario.getSenha().equals(senha);
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

    // ---- Novos Métodos de Relatório e Estatísticas ----

    public String gerarRelatorioDeAcessos(String formato) throws ExcecoesRepositorio {
        List<Usuario> usuarios = gerenciamentoUsuario.listarUsuarios();
        RelatorioTemplate relatorio;

        if ("html".equalsIgnoreCase(formato)) {
            relatorio = new RelatorioHTML();
        } else {
            relatorio = new RelatorioPDF(); // PDF como padrão
        }

        return relatorio.gerarRelatorio(usuarios);
    }

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
    // ---- Métodos relacionados ao padrão Memento ----
    /**
     * Salva o estado atual dos usuários.
     * @param descricao descrição do ponto de salvamento
     */
    public void salvarEstadoUsuarios(String descricao) throws ExcecoesRepositorio {
        List<Usuario> usuarios = gerenciamentoUsuario.listarUsuarios();
        UsuarioMemento memento = new UsuarioMemento(usuarios, descricao);
        mementoCaretaker.salvarMemento(memento);
    }
    
    /**
     * Salva o estado atual dos documentos.
     * @param descricao descrição do ponto de salvamento
     */
    public void salvarEstadoDocumentos(String descricao) throws ExcecoesRepositorio {
        List<Documento> documentos = gerenciamentoDocumento.listarDocumentos();
        DocumentoMemento memento = new DocumentoMemento(documentos, descricao);
        mementoCaretaker.salvarMemento(memento);
    }
    
    /**
     * Restaura o último estado salvo dos usuários.
     * @return true se restaurou com sucesso, false se não há estados salvos
     */
    public boolean restaurarUltimoEstadoUsuarios() throws ExcecoesRepositorio {
        if (!mementoCaretaker.temMementos()) {
            return false;
        }
        
        // Busca o último memento de usuários
        for (int i = mementoCaretaker.getQuantidadeMementos() - 1; i >= 0; i--) {
            var memento = mementoCaretaker.getMemento(i);
            if (memento instanceof UsuarioMemento usuarioMemento) {
                gerenciamentoUsuario.restaurarEstado(usuarioMemento.getUsuarios());
                return true;
            }
        }
        return false;
    }
    
    /**
     * Restaura o último estado salvo dos documentos.
     * @return true se restaurou com sucesso, false se não há estados salvos
     */
    public boolean restaurarUltimoEstadoDocumentos() throws ExcecoesRepositorio {
        if (!mementoCaretaker.temMementos()) {
            return false;
        }
        
        // Busca o último memento de documentos
        for (int i = mementoCaretaker.getQuantidadeMementos() - 1; i >= 0; i--) {
            var memento = mementoCaretaker.getMemento(i);
            if (memento instanceof DocumentoMemento documentoMemento) {
                gerenciamentoDocumento.restaurarEstado(documentoMemento.getDocumentos());
                return true;
            }
        }
        return false;
    }
    
    /**
     * Retorna o histórico de estados salvos.
     * @return lista com descrições dos mementos
     */
    public List<String> getHistoricoMementos() {
        return mementoCaretaker.getHistorico();
    }
    
    /**
     * Verifica se há estados salvos para restaurar.
     * @return true se há mementos disponíveis
     */
    public boolean temEstadosSalvos() {
        return mementoCaretaker.temMementos();
    }
}