package infra;

import entidade.Documento;
import java.util.List;
import utils.ExcecoesRepositorio;


public interface DocumentoRepositorio {
    void adicionar(Documento documento) throws ExcecoesRepositorio;
    void remover(String nome) throws ExcecoesRepositorio;
    List<Documento> listar() throws ExcecoesRepositorio;
    Documento buscarPorNome(String nome) throws ExcecoesRepositorio;
    List<Documento> buscarPorUsuario(String usuario) throws ExcecoesRepositorio;
}