package auth;

import facade.FacadeSingletonController;
import ui.AdminUI;
import java.util.Scanner;

public class AdminAuthenticator implements Authenticator {
    private final FacadeSingletonController fachada;
    private final Scanner scanner;
    private String usuario;

    public AdminAuthenticator(FacadeSingletonController fachada, Scanner scanner) {
        this.fachada = fachada;
        this.scanner = scanner;
    }

    @Override
    public boolean authenticate(String usuario, String senha) {
        this.usuario = usuario;
        return "admin".equals(usuario) && "admin".equals(senha);
    }

    @Override
    public String getTipoUsuario() {
        return "admin";
    }

    @Override
    public void redirecionarParaUI() {
        System.out.println("Login realizado como administrador.");
        AdminUI adminUI = new AdminUI(fachada, scanner);
        adminUI.iniciar();
    }
}