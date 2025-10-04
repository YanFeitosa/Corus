package command;

import entidade.Documento;
import facade.FacadeSingletonController;
import utils.ExcecoesRepositorio;

public class ExcluirDocumentoCommand implements Command {
    private final FacadeSingletonController facade;
    private final String nome;
    private Documento documentoRemovido;
    private boolean executed = false;

    public ExcluirDocumentoCommand(FacadeSingletonController facade, String nome) {
        this.facade = facade;
        this.nome = nome;
    }

    @Override
    public void execute() throws ExcecoesRepositorio {
        documentoRemovido = facade.buscarDocumentoPorNome(nome);
        if (documentoRemovido != null) {
            facade.removerDocumento(nome);
            executed = true;
        }
    }

    @Override
    public void undo() throws ExcecoesRepositorio {
        if (executed && documentoRemovido != null) {
            facade.cadastrarDocumento(
                documentoRemovido.getNome(), 
                documentoRemovido.getTamanho(), 
                documentoRemovido.getUsuarioAssociado()
            );
            executed = false;
        }
    }

    @Override
    public String getDescription() {
        return "Excluir documento: " + nome;
    }
}