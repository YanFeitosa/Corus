package infra;

import entidade.Usuario;
import java.util.ArrayList;
import java.util.List;
import utils.ExcecoesRepositorio;

public class PersistenciaMemoriaUsu implements UsuarioRepositorio {
    private List<Usuario> usuarios = new ArrayList<>();

    @Override
    public void adicionar(Usuario usuario) throws ExcecoesRepositorio {
        if (buscar(usuario.getLogin()) != null) {
            throw new ExcecoesRepositorio("Usuário já existe");
        }
        usuarios.add(usuario);
    }

    @Override
    public void remover(String login) throws ExcecoesRepositorio {
        Usuario u = buscar(login);
        if (u == null) {
            throw new ExcecoesRepositorio("Usuário não encontrado para remoção");
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
    public List<Usuario> listar() {
        return new ArrayList<>(usuarios);
    }
}