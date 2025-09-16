import facade.FacadeSingletonController;
import infra.DocumentoRepositorio;
import infra.PersistenciaArquivoUsu;
import infra.PersistenciaMemoriaDoc;
import infra.PersistenciaMemoriaUsu;
import infra.UsuarioRepositorio;
import java.util.Scanner;
import ui.AdminUI;
import ui.UserUI;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Escolha o tipo de persistência para Usuário:");
        System.out.println("1 - Memória");
        System.out.println("2 - Arquivo");
        System.out.print("Opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        UsuarioRepositorio usuarioRepositorio;
        if (opcao == 2) {
            usuarioRepositorio = new PersistenciaArquivoUsu();
            System.out.println("Persistência em arquivo selecionada.");
        } else {
            usuarioRepositorio = new PersistenciaMemoriaUsu();
            System.out.println("Persistência em memória selecionada.");
        }

        // Inicializar repositório de documentos
        DocumentoRepositorio documentoRepositorio = new PersistenciaMemoriaDoc();

        // Criar a facade passando os repositórios
        // Inicializar a fachada singleton
        FacadeSingletonController facade = FacadeSingletonController.getInstance(
            usuarioRepositorio, documentoRepositorio
        );

        telaLogin(facade, scanner);
    }

    private static void telaLogin(FacadeSingletonController fachada, Scanner scanner) {
        while (true) {
            System.out.println("\n===== Sistema de Login =====");
            System.out.println("1 - Entrar");
            System.out.println("0 - Sair do programa");
            System.out.print("Opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    realizarLogin(fachada, scanner);
                    break;
                case "0":
                    System.out.println("Encerrando programa...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void realizarLogin(FacadeSingletonController fachada, Scanner scanner) {
        System.out.println("\n--- Login ---");
        System.out.print("Usuário: ");
        String usuario = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        
        try {
            if (usuario.equals("admin") && senha.equals("admin")) {
                // Login como admin
                System.out.println("Login realizado como administrador.");
                AdminUI adminUI = new AdminUI(fachada, scanner);
                adminUI.iniciar();
            } else {
                // Verificar se é um usuário cadastrado
                if (fachada.verificarUsuario(usuario, senha)) {
                    System.out.println("Login realizado com sucesso.");
                    UserUI userUI = new UserUI(fachada, scanner, usuario);
                    userUI.iniciar();
                } else {
                    System.out.println("Usuário ou senha inválidos.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro durante o login: " + e.getMessage());
        }
    }
}


