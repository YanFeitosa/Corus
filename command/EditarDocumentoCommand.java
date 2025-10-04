package command;

import entidade.Documento;
import facade.FacadeSingletonController;
import utils.ExcecoesRepositorio;

public class EditarDocumentoCommand implements Command {
    private final FacadeSingletonController facade;
    private final String nomeAntigo;
    private final String novoNome;
    private final int novoTamanho;
    private final String usuarioAssociado;
    private Documento documentoOriginal;
    private boolean executed = false;

    public EditarDocumentoCommand(FacadeSingletonController facade, String nomeAntigo, String novoNome, int novoTamanho, String usuarioAssociado) {
        this.facade = facade;
        this.nomeAntigo = nomeAntigo;
        this.novoNome = novoNome;
        this.novoTamanho = novoTamanho;
        this.usuarioAssociado = usuarioAssociado;
    }

    @Override
    public void execute() throws ExcecoesRepositorio {
        // Salva o estado original para poder desfazer
        documentoOriginal = facade.buscarDocumentoPorNome(nomeAntigo);
        if (documentoOriginal != null) {
            facade.removerDocumento(nomeAntigo);
            facade.cadastrarDocumento(novoNome, novoTamanho, usuarioAssociado);
            executed = true;
        }
    }

    @Override
    public void undo() throws ExcecoesRepositorio {
        if (executed && documentoOriginal != null) {
            facade.removerDocumento(novoNome);
            facade.cadastrarDocumento(
                documentoOriginal.getNome(), 
                documentoOriginal.getTamanho(), 
                documentoOriginal.getUsuarioAssociado()
            );
            executed = false;
        }
    }

    @Override
    public String getDescription() {
        return "Editar documento: " + nomeAntigo + " -> " + novoNome + " (Novo tamanho: " + novoTamanho + ")";
    }
}