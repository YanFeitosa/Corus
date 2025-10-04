package facade;

import command.*;
import entidade.Documento;
import entidade.Usuario;
import infra.RepositorioFactory;
import utils.ExcecoesRepositorio;
import java.util.List;

/**
 * Fachada que implementa o padrão Command para operações da UserUI.
 * Especializada em operações de documentos com suporte a undo/redo.
 */
public class FacadeCommand {
    private static FacadeCommand instance;
    private final FacadeSingletonController facadeOriginal;
    private final CommandInvoker commandInvoker;

    private FacadeCommand(RepositorioFactory factory) {
        this.facadeOriginal = FacadeSingletonController.getInstance(factory);
        this.commandInvoker = new CommandInvoker();
    }

    public static synchronized FacadeCommand getInstance(RepositorioFactory factory) {
        if (instance == null) {
            instance = new FacadeCommand(factory);
        }
        return instance;
    }

    // ---- Métodos que utilizam Command Pattern ----

    /**
     * Cria um documento usando Command Pattern (com suporte a undo/redo)
     */
    public void criarDocumentoCommand(String nome, int tamanho, String usuarioAssociado) throws ExcecoesRepositorio {
        Command command = new CriarDocumentoCommand(facadeOriginal, nome, tamanho, usuarioAssociado);
        commandInvoker.executeCommand(command);
    }

    /**
     * Edita um documento usando Command Pattern (com suporte a undo/redo)
     */
    public void editarDocumentoCommand(String nomeAntigo, String novoNome, int novoTamanho, String usuarioAssociado) throws ExcecoesRepositorio {
        Command command = new EditarDocumentoCommand(facadeOriginal, nomeAntigo, novoNome, novoTamanho, usuarioAssociado);
        commandInvoker.executeCommand(command);
    }

    /**
     * Exclui um documento usando Command Pattern (com suporte a undo/redo)
     */
    public void excluirDocumentoCommand(String nome) throws ExcecoesRepositorio {
        Command command = new ExcluirDocumentoCommand(facadeOriginal, nome);
        commandInvoker.executeCommand(command);
    }

    // ---- Operações de Controle de Comandos ----

    public void undo() throws ExcecoesRepositorio {
        commandInvoker.undo();
    }

    public void redo() throws ExcecoesRepositorio {
        commandInvoker.redo();
    }

    public String getCommandHistory() {
        return commandInvoker.getHistory();
    }

    public boolean canUndo() {
        return commandInvoker.canUndo();
    }

    public boolean canRedo() {
        return commandInvoker.canRedo();
    }

    public void clearCommandHistory() {
        commandInvoker.clearHistory();
    }

    public int getCommandHistorySize() {
        return commandInvoker.getHistorySize();
    }

    // ---- Métodos de Delegação para operações de consulta ----

    public List<Documento> listarDocumentos() throws ExcecoesRepositorio {
        return facadeOriginal.listarDocumentos();
    }

    public Documento buscarDocumentoPorNome(String nome) throws ExcecoesRepositorio {
        return facadeOriginal.buscarDocumentoPorNome(nome);
    }

    public Usuario buscarUsuario(String login) throws ExcecoesRepositorio {
        return facadeOriginal.buscarUsuario(login);
    }

    public void atualizarUsuario(Usuario usuario) throws ExcecoesRepositorio {
        facadeOriginal.atualizarUsuario(usuario);
    }

    public boolean verificarUsuario(String login, String senha) throws ExcecoesRepositorio {
        return facadeOriginal.verificarUsuario(login, senha);
    }

    // Método para resetar a instância (útil para testes)
    public static synchronized void resetInstance() {
        instance = null;
    }
}