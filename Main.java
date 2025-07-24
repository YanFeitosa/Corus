import repositorio.UsuarioRepositorio;
import controle.GerenciamentoUsuario;
import ui.AdminUI;

public class Main {
    public static void main(String[] args) {
        UsuarioRepositorio repositorio = new UsuarioRepositorio();
        GerenciamentoUsuario controlador = new GerenciamentoUsuario(repositorio);
        AdminUI ui = new AdminUI(controlador);

        ui.iniciar();
    }
}
