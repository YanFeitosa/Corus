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
        
        repositorio.adicionar(new Usuario(login, senha));
    }

    public List<Usuario> listarUsuarios() throws ExcecoesRepositorio {
        return repositorio.listar();
    }
}