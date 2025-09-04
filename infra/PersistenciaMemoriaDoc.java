package infra;

import entidade.Documento;
import utils.ExcecoesRepositorio;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaMemoriaDoc implements DocumentoRepositorio {
    private List<Documento> documentos = new ArrayList<>();

    @Override
    public void adicionar(Documento documento) throws ExcecoesRepositorio {
        if (buscarPorNome(documento.getNome()) != null) {
            throw new ExcecoesRepositorio("Documento com este nome já existe");
        }
        documentos.add(documento);
    }

    @Override
    public List<Documento> listar() throws ExcecoesRepositorio {
        return new ArrayList<>(documentos);
    }

    @Override
    public Documento buscarPorNome(String nome) throws ExcecoesRepositorio {
        for (Documento doc : documentos) {
            if (doc.getNome().equals(nome)) {
                return doc;
            }
        }
        return null;
    }

    @Override
    public List<Documento> buscarPorUsuario(String usuario) throws ExcecoesRepositorio {
        List<Documento> documentosUsuario = new ArrayList<>();
        for (Documento doc : documentos) {
            if (doc.getUsuarioAssociado().equals(usuario)) {
                documentosUsuario.add(doc);
            }
        }
        return documentosUsuario;
    }
}