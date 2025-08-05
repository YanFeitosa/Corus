package ui;

import controle.GerenciamentoUsuario;
import entidade.Usuario;
import java.util.List;
import java.util.Scanner;
import utils.ExcecoesLogin;
import utils.ExcecoesRepositorio;
import utils.ExcecoesSenha;

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
        System.out.println("\n--- Novo Usuário ---");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            controlador.adicionarUsuario(login, senha);
            System.out.println("Usuário adicionado com sucesso!");
        } catch (ExcecoesLogin e) {
            System.out.println("Erro no login: " + e.getMessage());
            System.out.println("Dica: Login deve ter até 12 letras sem números");
        } catch (ExcecoesSenha e) {
            System.out.println("Erro na senha: " + e.getMessage());
            System.out.println("Requisitos da senha:");
            System.out.println("- Mínimo 8 caracteres");
            System.out.println("- Letra maiúscula e minúscula");
            System.out.println("- Número e caractere especial");
            System.out.println("- Não pode ser igual ao login");
        } catch (ExcecoesRepositorio e) {
            System.out.println("Erro no sistema: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

 private void listarUsuarios() {
        try {
            List<Usuario> usuarios = controlador.listarUsuarios();
            System.out.println("\n--- Lista Completa de Usuários ---");
            if (usuarios.isEmpty()) {
                System.out.println("Nenhum usuário cadastrado");
            } else {
                System.out.println("+------------+--------------------------------+");
                System.out.println("| Login      | Senha                          |");
                System.out.println("+------------+--------------------------------+");
                
                for (Usuario u : usuarios) {
                    System.out.printf("| %-10s | %-30s |%n", u.getLogin(), u.getSenha());
                }
                
                System.out.println("+------------+--------------------------------+");
                System.out.println("Total de usuários: " + usuarios.size());
            }
        } catch (ExcecoesRepositorio e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }
    }
}