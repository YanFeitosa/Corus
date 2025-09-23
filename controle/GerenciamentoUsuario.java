package controle;

import entidade.Usuario;
import infra.UsuarioRepositorio;
import java.util.List;
import utils.ExcecoesLogin;
import utils.ExcecoesRepositorio;
import utils.ExcecoesSenha;

public class GerenciamentoUsuario {
    private UsuarioRepositorio repositorio;

    public GerenciamentoUsuario(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void adicionarUsuario(String login, String senha)
            throws ExcecoesRepositorio, ExcecoesLogin, ExcecoesSenha {

        // Validações delegadas às classes de exceção
        ExcecoesLogin.validar(login);
        ExcecoesSenha.validar(senha, login);

        repositorio.adicionar(new Usuario(login, senha, 0, null));
    }

    public List<Usuario> listarUsuarios() throws ExcecoesRepositorio {
        return repositorio.listar();
    }

    public Usuario buscarUsuario(String login) throws ExcecoesRepositorio {
        return repositorio.buscar(login);
    }

    public void atualizarUsuario(Usuario usuario) throws ExcecoesRepositorio {
        repositorio.atualizar(usuario);
    }

    public void removerUsuario(String login) throws ExcecoesRepositorio {
        // Apenas delega a chamada para a camada de persistência
        repositorio.remover(login);
    }
    
    /**
     * Restaura o estado dos usuários para um estado anterior (Padrão Memento).
     * Remove todos os usuários atuais e adiciona os do estado salvo.
     * @param usuariosSalvos lista de usuários do estado anterior
     */
    public void restaurarEstado(List<Usuario> usuariosSalvos) throws ExcecoesRepositorio {
        // Remove todos os usuários atuais
        List<Usuario> usuariosAtuais = repositorio.listar();
        for (Usuario u : usuariosAtuais) {
            repositorio.remover(u.getLogin());
        }
        
        // Adiciona os usuários do estado salvo
        for (Usuario u : usuariosSalvos) {
            repositorio.adicionar(u);
        }
    }
}