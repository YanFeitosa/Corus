package auth;

import facade.FacadeSingletonController;
import ui.AdminUI;
import ui.UserUI;
import utils.ExcecoesRepositorio;

import java.util.Scanner;

public class AuthenticationManager {
    private final FacadeSingletonController fachada;
    private final Scanner scanner;

    public AuthenticationManager(FacadeSingletonController fachada, Scanner scanner) {
        this.fachada = fachada;
        this.scanner = scanner;
    }

    public void iniciarSistema() {
        while (true) {
            exibirMenuLogin();
            String opcao = scanner.nextLine();
            
            switch (opcao) {
                case "1":
                    processarLogin();
                    break;
                case "0":
                    System.out.println("Encerrando programa...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void exibirMenuLogin() {
        System.out.println("\n===== Sistema de Login =====");
        System.out.println("1 - Entrar");
        System.out.println("0 - Sair do programa");
        System.out.print("Opção: ");
    }

    private void processarLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Usuário: ");
        String usuario = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            if (autenticarAdmin(usuario, senha)) {
                System.out.println("Login realizado como administrador.");
                AdminUI adminUI = new AdminUI(fachada, scanner);
                adminUI.iniciar();
            } else if (autenticarUsuario(usuario, senha)) {
                System.out.println("Login realizado com sucesso.");
                UserUI userUI = new UserUI(fachada, scanner, usuario);
                userUI.iniciar();
            } else {
                System.out.println("Usuário ou senha inválidos.");
            }
        } catch (ExcecoesRepositorio e) {
            System.out.println("Erro durante o login: " + e.getMessage());
        }
    }

    private boolean autenticarAdmin(String usuario, String senha) {
        return "admin".equals(usuario) && "admin".equals(senha);
    }

    private boolean autenticarUsuario(String usuario, String senha) throws ExcecoesRepositorio {
        return fachada.verificarUsuario(usuario, senha);
    }
}