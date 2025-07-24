package ui;

import controle.GerenciamentoUsuario;
import entidade.Usuario;
import java.util.List;
import java.util.Scanner;

public class AdminUI {
    private GerenciamentoUsuario controlador;
    private Scanner scanner;

    public AdminUI(GerenciamentoUsuario controlador) {
        this.controlador = controlador;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n===== Menu =====");
            System.out.println("1 - Adicionar usuário");
            System.out.println("2 - Listar usuários");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    adicionarUsuario();
                    break;
                case "2":
                    listarUsuarios();
                    break;
                case "0":
                    System.out.println("Encerrando...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void adicionarUsuario() {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        controlador.adicionarUsuario(login, senha);
        System.out.println("Usuário adicionado.");
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = controlador.listarUsuarios();
        System.out.println("\n--- Lista de Usuários ---");
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
        if (usuarios.isEmpty()) {
            System.out.println("(Nenhum usuário cadastrado)");
        }
    }
}
