    import controle.GerenciamentoUsuario;
    import infra.PersistenciaArquivo;
    import infra.PersistenciaMemoria;
    import infra.UsuarioRepositorio;
    import java.util.Scanner;
    import ui.AdminUI;

    public class Main {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Escolha o tipo de persistência:");
            System.out.println("1 - Memória");
            System.out.println("2 - Arquivo");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            UsuarioRepositorio repositorio;
            if (opcao == 2) {
                repositorio = new PersistenciaArquivo();
                System.out.println("Persistência em arquivo selecionada.");
            } else {
                repositorio = new PersistenciaMemoria();
                System.out.println("Persistência em memória selecionada.");
            }

            GerenciamentoUsuario controlador = new GerenciamentoUsuario(repositorio);
            AdminUI ui = new AdminUI(controlador);
            ui.iniciar();
        }
    }