package command;

//import entidade.Documento;
import facade.FacadeSingletonController;
import utils.ExcecoesRepositorio;

public class CriarDocumentoCommand implements Command {
    private final FacadeSingletonController facade;
    private final String nome;
    private final int tamanho;
    private final String usuarioAssociado;
    private boolean executed = false;

    public CriarDocumentoCommand(FacadeSingletonController facade, String nome, int tamanho, String usuarioAssociado) {
        this.facade = facade;
        this.nome = nome;
        this.tamanho = tamanho;
        this.usuarioAssociado = usuarioAssociado;
    }

    @Override
    public void execute() throws ExcecoesRepositorio {
        facade.cadastrarDocumento(nome, tamanho, usuarioAssociado);
        executed = true;
    }

    @Override
    public void undo() throws ExcecoesRepositorio {
        if (executed) {
            facade.removerDocumento(nome);
            executed = false;
        }
    }

    @Override
    public String getDescription() {
        return "Criar documento: " + nome + " (Tamanho: " + tamanho + ")";
    }
}