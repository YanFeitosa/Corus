// Crie o novo arquivo: controle/RelatorioPDF.java
package controle;

import entidade.Usuario;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatorioPDF extends RelatorioTemplate {
    @Override
    protected String gerarCabecalho() {
        return "========================================================\n" +
                "|           RELATORIO DE ACESSO DE USUARIOS            |\n" +
                "========================================================\n" +
                "| LOGIN      | QTD. ACESSOS | ULTIMO LOGIN (dd/MM/yyyy) |\n" +
                "--------------------------------------------------------\n";
    }

    @Override
    protected String gerarCorpo(List<Usuario> usuarios) {
        StringBuilder corpo = new StringBuilder();
        for (Usuario u : usuarios) {
            corpo.append(String.format("| %-10s | %-12d | %-25s |\n",
                    u.getLogin(), u.getContagemDeAcessos(), u.getUltimoLoginFormatado()));
        }
        return corpo.toString();
    }

    @Override
    protected String gerarRodape() {
        String dataGeracao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return "--------------------------------------------------------\n" +
                String.format("| Gerado em: %-38s |\n", dataGeracao) +
                "========================================================\n";
    }
}