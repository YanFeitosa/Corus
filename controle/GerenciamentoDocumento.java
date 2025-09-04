package controle;

import entidade.Documento;
import infra.DocumentoRepositorio;
import infra.UsuarioRepositorio;
import utils.ExcecoesRepositorio;

import java.util.List;

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
        
        Documento documento = new Documento(nome, tamanho, usuarioAssociado);
        documentoRepositorio.adicionar(documento);
    }

    public List<Documento> listarDocumentos() throws ExcecoesRepositorio {
        return documentoRepositorio.listar();
    }
}