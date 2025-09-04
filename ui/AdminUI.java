package ui;

import controle.GerenciamentoDocumento;
import controle.GerenciamentoUsuario;
import entidade.Documento;
import entidade.Usuario;
import java.util.List;
import java.util.Scanner;
import utils.ExcecoesLogin;
import utils.ExcecoesRepositorio;
import utils.ExcecoesSenha;

public class AdminUI {
    private GerenciamentoUsuario controladorUsuario;
    private GerenciamentoDocumento controladorDocumento;
    private Scanner scanner;

    public AdminUI(GerenciamentoUsuario controladorUsuario, GerenciamentoDocumento controladorDocumento) {
        this.controladorUsuario = controladorUsuario;
        this.controladorDocumento = controladorDocumento;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n===== Menu Principal =====");
            System.out.println("1 - Gerenciar usuários");
            System.out.println("2 - Gerenciar documentos");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    gerenciarUsuarios();
                    break;
                case "2":
                    gerenciarDocumentos();
                    break;
                case "0":
                    System.out.println("Encerrando...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void gerenciarUsuarios() {
        while (true) {
            System.out.println("\n===== Gerenciamento de Usuários =====");
            System.out.println("1 - Adicionar usuário");
            System.out.println("2 - Listar usuários");
            System.out.println("0 - Voltar");
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
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void gerenciarDocumentos() {
        while (true) {
            System.out.println("\n===== Gerenciamento de Documentos =====");
            System.out.println("1 - Adicionar documento");
            System.out.println("2 - Listar documentos");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    adicionarDocumento();
                    break;
                case "2":
                    listarDocumentos();
                    break;
                case "0":
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
            controladorUsuario.adicionarUsuario(login, senha);
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
            System.out.println("- Sem espaços em branco");
        } catch (ExcecoesRepositorio e) {
            System.out.println("Erro no sistema: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    private void listarUsuarios() {
        try {
            List<Usuario> usuarios = controladorUsuario.listarUsuarios();
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

    private void adicionarDocumento() {
        System.out.println("\n--- Novo Documento ---");
        
        // Listar usuários disponíveis
        try {
            List<Usuario> usuarios = controladorUsuario.listarUsuarios();
            if (usuarios.isEmpty()) {
                System.out.println("Nenhum usuário cadastrado. É necessário cadastrar um usuário primeiro.");
                return;
            }
            
            System.out.println("Usuários disponíveis:");
            for (Usuario usuario : usuarios) {
                System.out.println("- " + usuario.getLogin());
            }
        } catch (ExcecoesRepositorio e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
            return;
        }
        
        System.out.print("Nome do documento: ");
        String nome = scanner.nextLine();
        
        System.out.print("Tamanho: ");
        int tamanho = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        
        System.out.print("Usuário associado: ");
        String usuarioAssociado = scanner.nextLine();

        try {
            controladorDocumento.adicionarDocumento(nome, tamanho, usuarioAssociado);
            System.out.println("Documento adicionado com sucesso!");
        } catch (ExcecoesRepositorio e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarDocumentos() {
        try {
            List<Documento> documentos = controladorDocumento.listarDocumentos();
            System.out.println("\n--- Lista de Documentos ---");
            if (documentos.isEmpty()) {
                System.out.println("Nenhum documento cadastrado");
            } else {
                System.out.println("+----------------------+-----------+------------------+");
                System.out.println("| Nome                 | Tamanho   | Usuário          |");
                System.out.println("+----------------------+-----------+------------------+");
                
                for (Documento doc : documentos) {
                    System.out.printf("| %-20s | %-9d | %-16s |%n", 
                            doc.getNome(), doc.getTamanho(), doc.getUsuarioAssociado());
                }
                
                System.out.println("+----------------------+-----------+------------------+");
                System.out.println("Total de documentos: " + documentos.size());
            }
        } catch (ExcecoesRepositorio e) {
            System.out.println("Erro ao listar documentos: " + e.getMessage());
        }
    }
}