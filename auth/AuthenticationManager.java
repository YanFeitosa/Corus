package auth;

import facade.FacadeSingletonController;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.ExcecoesLogin;
import utils.ExcecoesRepositorio;
import utils.ExcecoesSenha;

public class AuthenticationManager {
    private final FacadeSingletonController fachada;
    private final Scanner scanner;
    private final List<Authenticator> authenticators;

    public AuthenticationManager(FacadeSingletonController fachada, Scanner scanner) {
        this.fachada = fachada;
        this.scanner = scanner;
        this.authenticators = new ArrayList<>();
        
        // Registrar os adaptadores de autenticação
        authenticators.add(new AdminAuthenticator(fachada, scanner));
        authenticators.add(new UserAuthenticator(fachada, scanner));
    }

    // Método para adicionar novos adaptadores (extensibilidade)
    public void registrarAuthenticator(Authenticator authenticator) {
        authenticators.add(authenticator);
    }

    public void iniciarSistema() {
        while (true) {
            System.out.println("\n===== Sistema de Login =====");
            System.out.println("1 - Entrar");
            System.out.println("0 - Sair do programa");
            System.out.print("Opção: ");
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

    private void processarLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Usuário: ");
        String usuario = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            for (Authenticator auth : authenticators) {
                if (auth.authenticate(usuario, senha)) {
                    auth.redirecionarParaUI();
                    return;
                }
            }
            System.out.println("Usuário ou senha inválidos.");
        } catch (ExcecoesLogin e) {
            System.out.println("Erro de validação do login: " + e.getMessage());
        } catch (ExcecoesSenha e) {
            System.out.println("Erro de validação da senha: " + e.getMessage());
        } catch (ExcecoesRepositorio e) {
            System.out.println("Erro durante o login: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }
}