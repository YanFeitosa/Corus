package controle;

import entidade.Documento;
import entidade.DocumentoBuilder;
import infra.DocumentoRepositorio;
import infra.UsuarioRepositorio;
import java.util.List;
import utils.ExcecoesRepositorio;

public class GerenciamentoDocumento {
    private DocumentoRepositorio documentoRepositorio;
    private UsuarioRepositorio usuarioRepositorio;

    public GerenciamentoDocumento(DocumentoRepositorio documentoRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.documentoRepositorio = documentoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void adicionarDocumento(String nome, int tamanho, String usuarioAssociado) throws ExcecoesRepositorio {
        // Verificar se o usuário existe
        if (usuarioRepositorio.buscar(usuarioAssociado) == null) {
            throw new ExcecoesRepositorio("Usuário não encontrado");
        }
        // ------ Construção direta ------
        // Documento documento = new Documento(nome, tamanho, usuarioAssociado);
        // documentoRepositorio.adicionar(documento);

        // ------ Construção usando o Builder ------
        Documento documento = new DocumentoBuilder()
        .withNome(nome)
        .withTamanho(tamanho)
        .withUsuarioAssociado(usuarioAssociado)
        .build();

        documentoRepositorio.adicionar(documento);

    }

    public void removerDocumento(String nome) throws ExcecoesRepositorio {
        documentoRepositorio.remover(nome);
    }

    public Documento buscarDocumentoPorNome(String nome) throws ExcecoesRepositorio {
        return documentoRepositorio.buscarPorNome(nome);
    }

    public List<Documento> listarDocumentos() throws ExcecoesRepositorio {
        return documentoRepositorio.listar();
    }
    
    /**
     * Restaura o estado dos documentos para um estado anterior (Padrão Memento).
     * Remove todos os documentos atuais e adiciona os do estado salvo.
     * @param documentosSalvos lista de documentos do estado anterior
     */
    public void restaurarEstado(List<Documento> documentosSalvos) throws ExcecoesRepositorio {
        // Remove todos os documentos atuais
        List<Documento> documentosAtuais = documentoRepositorio.listar();
        for (Documento d : documentosAtuais) {
            documentoRepositorio.remover(d.getNome());
        }
        
        // Adiciona os documentos do estado salvo
        for (Documento d : documentosSalvos) {
            documentoRepositorio.adicionar(d);
        }
    }
}