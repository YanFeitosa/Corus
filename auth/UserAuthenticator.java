package auth;

import facade.FacadeSingletonController;
import ui.UserUI;
import utils.ExcecoesLogin;
import utils.ExcecoesRepositorio;
import utils.ExcecoesSenha;
import java.util.Scanner;

public class UserAuthenticator implements Authenticator {
    private final FacadeSingletonController fachada;
    private final Scanner scanner;
    private String usuario;

    public UserAuthenticator(FacadeSingletonController fachada, Scanner scanner) {
        this.fachada = fachada;
        this.scanner = scanner;
    }

    @Override
    public boolean authenticate(String usuario, String senha) throws ExcecoesLogin, ExcecoesSenha, ExcecoesRepositorio {
        // Validar formato das credenciais
        ExcecoesLogin.validar(usuario);
        ExcecoesSenha.validar(senha, usuario);
        
        this.usuario = usuario;
        return fachada.verificarUsuario(usuario, senha);
    }

    @Override
    public String getTipoUsuario() {
        return "user";
    }

    @Override
    public void redirecionarParaUI() {
        System.out.println("Login realizado com sucesso.");
        UserUI userUI = new UserUI(fachada, scanner, usuario);
        userUI.iniciar();
    }
}