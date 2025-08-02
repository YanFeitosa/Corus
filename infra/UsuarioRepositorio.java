// package repositorio;

// import entidade.Usuario;
// import java.util.ArrayList;
// import java.util.List;

// public class UsuarioRepositorio {
//     private List<Usuario> usuarios;

//     public UsuarioRepositorio() {
//         this.usuarios = new ArrayList<>();
//     }

//     public void salvar(Usuario usuario) {
//         usuarios.add(usuario);
//     }

//     public List<Usuario> buscarTodos() {
//         return usuarios;
//     }
// }





package infra;

import entidade.Usuario;
import java.util.List;

public interface UsuarioRepositorio {
    void adicionar(Usuario usuario) throws Exception;
    void remover(String login) throws Exception;
    Usuario buscar(String login) throws Exception;
    List<Usuario> listar() throws Exception;
}

