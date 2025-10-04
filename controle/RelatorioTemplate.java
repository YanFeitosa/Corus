package controle;

import entidade.Relatorio;
import java.util.List;

public abstract class RelatorioTemplate {

    // Template Method modificado para trabalhar com List<Relatorio>
    public final String gerarRelatorio(List<Relatorio> relatorios) {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append(gerarCabecalho());
        relatorio.append(gerarCorpo(relatorios));
        relatorio.append(gerarRodape());
        return relatorio.toString();
    }

    // Métodos abstratos que as subclasses implementarão
    protected abstract String gerarCabecalho();
    protected abstract String gerarCorpo(List<Relatorio> relatorios);
    protected abstract String gerarRodape();
}