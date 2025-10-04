import auth.AuthenticationManager;
import facade.FacadeSingletonController;
import infra.ArquivoRepositorioFactory;
import infra.MemoriaRepositorioFactory;
import infra.RepositorioFactory;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Configurar persistência (já retorna a factory)
        RepositorioFactory factory = configurarPersistencia(scanner);

        // Inicializar a fachada singleton original
        FacadeSingletonController fachada = FacadeSingletonController.getInstance(factory);
        
        // Passar a factory para o AuthenticationManager
        AuthenticationManager authManager = new AuthenticationManager(fachada, scanner, factory);
        authManager.iniciarSistema();

        scanner.close();
    }

    private static RepositorioFactory configurarPersistencia(Scanner scanner) {
        System.out.println("Escolha o tipo de persistência:");
        System.out.println("1 - Memória");
        System.out.println("2 - Arquivo");
        System.out.print("Opção: ");
        String opcao = scanner.nextLine();

        if ("2".equals(opcao)) {
            System.out.println("Fábrica de persistência em ARQUIVO selecionada.");
            return new ArquivoRepositorioFactory();
        } else {
            System.out.println("Fábrica de persistência em MEMÓRIA selecionada.");
            return new MemoriaRepositorioFactory();
        }
    }
}