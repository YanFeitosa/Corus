package infra;

import entidade.Usuario;
import java.util.List;
import utils.ExcecoesRepositorio;

public interface UsuarioRepositorio {
    void adicionar(Usuario usuario) throws ExcecoesRepositorio;
    void remover(String login) throws ExcecoesRepositorio;
    Usuario buscar(String login) throws ExcecoesRepositorio;
    List<Usuario> listar() throws ExcecoesRepositorio;
}