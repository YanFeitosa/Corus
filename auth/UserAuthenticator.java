package auth;

import facade.FacadeSingletonController;
import ui.UserUI;
import utils.ExcecoesLogin;
import utils.ExcecoesRepositorio;
import utils.ExcecoesSenha;
import java.util.Scanner;

import entidade.Usuario;

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

        // Busca o usuário para verificar a senha E para poder atualizá-lo
        Usuario usuarioEncontrado = fachada.buscarUsuario(usuario);

        if (usuarioEncontrado != null && usuarioEncontrado.getSenha().equals(senha)) {
            // LOGIN BEM-SUCEDIDO!
            // 1. Registra o novo acesso no objeto
            usuarioEncontrado.registrarAcesso();

            // 2. Manda a Façade persistir a atualização
            fachada.atualizarUsuario(usuarioEncontrado);

            this.usuario = usuario;
            return true;
        }

        // Se o usuário não foi encontrado ou a senha está incorreta
        return false;
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