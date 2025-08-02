import infra.PersistenciaMemoria;
import infra.UsuarioRepositorio;
import controle.GerenciamentoUsuario;
import ui.AdminUI;

public class Main {
    public static void main(String[] args) {
        UsuarioRepositorio repositorio = new PersistenciaMemoria();
        GerenciamentoUsuario controlador = new GerenciamentoUsuario(repositorio);
        AdminUI ui = new AdminUI(controlador);

        ui.iniciar();
    }
}
