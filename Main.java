
// Arquivo: Main.java (totalmente refatorado)
import auth.AuthenticationManager;
import facade.FacadeSingletonController;
import infra.ArquivoRepositorioFactory;
import infra.MemoriaRepositorioFactory;
import infra.RepositorioFactory;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. O Main agora escolhe qual FÁBRICA usar
        RepositorioFactory factory = configurarPersistencia(scanner);

        // 2. A Fábrica é injetada na Façade. A Façade não sabe qual fábrica é.
        FacadeSingletonController fachada = FacadeSingletonController.getInstance(factory);

        // 3. O resto do sistema funciona normalmente
        AuthenticationManager authManager = new AuthenticationManager(fachada, scanner);
        authManager.iniciarSistema();

        scanner.close();
    }

    // esse método agora retorna a FÁBRICA ABSTRATA
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