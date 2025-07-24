import java.util.List;

public class GerenciamentoUsuario {
    private UsuarioRepositorio repositorio;

    public GerenciamentoUsuario(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void adicionarUsuario(String login, String senha) {
        Usuario novo = new Usuario(login, senha);
        repositorio.salvar(novo);
    }

    public List<Usuario> listarUsuarios() {
        return repositorio.buscarTodos();
    }
}
