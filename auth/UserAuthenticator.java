package auth;

import entidade.Usuario;
import facade.FacadeCommand;
import facade.FacadeSingletonController;
import infra.RepositorioFactory;
import java.util.Scanner;
import ui.UserUI;
import utils.ExcecoesLogin;
import utils.ExcecoesRepositorio;
import utils.ExcecoesSenha;

public class UserAuthenticator implements Authenticator {
    private final FacadeSingletonController fachada;
    private final Scanner scanner;
    private final RepositorioFactory factory; // NOVO: Para criar FacadeCommand
    private String usuario;

    // CONSTRUTOR ATUALIZADO: Recebe a factory
    public UserAuthenticator(FacadeSingletonController fachada, Scanner scanner, RepositorioFactory factory) {
        this.fachada = fachada;
        this.scanner = scanner;
        this.factory = factory;
    }

    @Override
    public boolean authenticate(String usuario, String senha) throws ExcecoesLogin, ExcecoesSenha, ExcecoesRepositorio {
        // A autenticação continua usando a fachada original
        ExcecoesLogin.validar(usuario);
        ExcecoesSenha.validar(senha, usuario);

        Usuario usuarioEncontrado = fachada.buscarUsuario(usuario);

        if (usuarioEncontrado != null && usuarioEncontrado.getSenha().equals(senha)) {
            usuarioEncontrado.registrarAcesso();
            fachada.atualizarUsuario(usuarioEncontrado);
            this.usuario = usuario;
            return true;
        }
        return false;
    }

    @Override
    public String getTipoUsuario() {
        return "user";
    }

    @Override
    public void redirecionarParaUI() {
        System.out.println("Login realizado com sucesso.");
        
        // NOVO: Usar FacadeCommand em vez de FacadeSingletonController
        FacadeCommand facadeCommand = FacadeCommand.getInstance(factory);
        UserUI userUI = new UserUI(facadeCommand, scanner, usuario);
        userUI.iniciar();
    }
}