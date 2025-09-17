import auth.AuthenticationManager;
import facade.FacadeSingletonController;
import infra.DocumentoRepositorio;
import infra.PersistenciaArquivoUsu;
import infra.PersistenciaMemoriaDoc;
import infra.PersistenciaMemoriaUsu;
import infra.UsuarioRepositorio;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Configurar persistência
        UsuarioRepositorio usuarioRepositorio = configurarPersistenciaUsuario(scanner);
        DocumentoRepositorio documentoRepositorio = new PersistenciaMemoriaDoc();

        // Inicializar a fachada singleton
        FacadeSingletonController fachada = FacadeSingletonController.getInstance(
            usuarioRepositorio, documentoRepositorio
        );
        
        // Iniciar sistema de autenticação
        AuthenticationManager authManager = new AuthenticationManager(fachada, scanner);
        authManager.iniciarSistema();
        
        scanner.close();
    }

    private static UsuarioRepositorio configurarPersistenciaUsuario(Scanner scanner) {
        System.out.println("Escolha o tipo de persistência para Usuário:");
        System.out.println("1 - Memória");
        System.out.println("2 - Arquivo");
        System.out.print("Opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        if (opcao == 2) {
            System.out.println("Persistência em arquivo selecionada.");
            return new PersistenciaArquivoUsu();
        } else {
            System.out.println("Persistência em memória selecionada.");
            return new PersistenciaMemoriaUsu();
        }
    }
}