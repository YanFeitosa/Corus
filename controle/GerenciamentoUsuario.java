package controle;

import entidade.Usuario;
import infra.UsuarioRepositorio;

import java.util.List;

public class GerenciamentoUsuario {
    private UsuarioRepositorio repositorio;

    public GerenciamentoUsuario(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void adicionarUsuario(String login, String senha) throws Exception {
        Usuario novo = new Usuario(login, senha);
        repositorio.adicionar(novo);
    }

    public List<Usuario> listarUsuarios() throws Exception {
        return repositorio.listar();
    }
}
