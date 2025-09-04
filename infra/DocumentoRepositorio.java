package infra;

import entidade.Documento;
import utils.ExcecoesRepositorio;

import java.util.List;

public interface DocumentoRepositorio {
    void adicionar(Documento documento) throws ExcecoesRepositorio;
    List<Documento> listar() throws ExcecoesRepositorio;
    Documento buscarPorNome(String nome) throws ExcecoesRepositorio;
    List<Documento> buscarPorUsuario(String usuario) throws ExcecoesRepositorio;
}