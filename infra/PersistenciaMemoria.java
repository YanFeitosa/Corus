package infra;

import entidade.Usuario;
// import utils.ExcecoesRepositorio;
import infra.UsuarioRepositorio;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaMemoria implements UsuarioRepositorio {
    private List<Usuario> usuarios = new ArrayList<>();

    @Override
    public void adicionar(Usuario usuario) throws Exception {
        if (buscar(usuario.getLogin()) != null) {
            // throw new ExcecoesRepositorio("Usuário já existe na memória.");
            System.out.println("Usuário já existe na memória.");
        }
        usuarios.add(usuario);
    }

    @Override
    public void remover(String login) throws Exception {
        Usuario u = buscar(login);
        if (u == null) {
            System.out.println("Usuário não encontrado para remoção.");
            // throw new ExcecoesRepositorio("Usuário não encontrado para remoção.");
        }
        usuarios.remove(u);
    }

    @Override
    public Usuario buscar(String login) {
        for (Usuario u : usuarios) {
            if (u.getLogin().equals(login)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> listar() throws Exception {
        return new ArrayList<>(usuarios);
    }
}
