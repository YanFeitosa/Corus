import facade.FacadeSingletonController;
import infra.DocumentoRepositorio;
import infra.PersistenciaArquivoUsu;
import infra.PersistenciaMemoriaDoc;
import infra.PersistenciaMemoriaUsu;
import infra.UsuarioRepositorio;
import java.util.Scanner;
import ui.AdminUI;
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
        FacadeSingletonController sistema = new FacadeSingletonController(usuarioRepositorio, documentoRepositorio);
        // --------------------- sem facade ---------------------------
        // GerenciamentoUsuario controladorUsuario = new GerenciamentoUsuario(usuarioRepositorio);
        // GerenciamentoDocumento controladorDocumento = new GerenciamentoDocumento(documentoRepositorio, usuarioRepositorio);

        // AdminUI ui = new AdminUI(controladorUsuario, controladorDocumento);
        AdminUI ui = new AdminUI(sistema);
        ui.iniciar();
    }
}