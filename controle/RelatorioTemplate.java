package controle;

import entidade.Usuario;
import java.util.List;

public abstract class RelatorioTemplate {

    // Este é o Template Method. Ele define a estrutura do algoritmo.
    public final String gerarRelatorio(List<Usuario> usuarios) {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append(gerarCabecalho());
        relatorio.append(gerarCorpo(usuarios));
        relatorio.append(gerarRodape());
        return relatorio.toString();
    }

    // Métodos que as subclasses serão forçadas a implementar
    protected abstract String gerarCabecalho();

    protected abstract String gerarCorpo(List<Usuario> usuarios);

    protected abstract String gerarRodape();
}