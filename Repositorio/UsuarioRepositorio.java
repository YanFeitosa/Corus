package repositorio;

import entidade.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio {
    private List<Usuario> usuarios;

    public UsuarioRepositorio() {
        this.usuarios = new ArrayList<>();
    }

    public void salvar(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Usuario> buscarTodos() {
        return usuarios;
    }
}
