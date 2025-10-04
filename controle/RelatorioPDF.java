package controle;

import entidade.Relatorio;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatorioPDF extends RelatorioTemplate {
    @Override
    protected String gerarCabecalho() {
        return "================================================================================\n" +
               "|                      RELATORIO DE ACESSO DE USUARIOS                        |\n" +
               "================================================================================\n" +
               "| LOGIN      | ACESSOS | STATUS            | ULTIMO LOGIN         | TIPO      |\n" +
               "--------------------------------------------------------------------------------\n";
    }

    @Override
    protected String gerarCorpo(List<Relatorio> relatorios) {
        StringBuilder corpo = new StringBuilder();
        for (Relatorio rel : relatorios) {
            corpo.append(String.format("| %-10s | %-7d | %-16s | %-20s | %-9s |\n",
                    rel.getLogin(),
                    rel.getQuantidadeAcessos(),
                    rel.getStatusAcesso(),
                    rel.getUltimoAcessoFormatado(),
                    rel.getTipoUsuario()));
        }
        return corpo.toString();
    }

    @Override
    protected String gerarRodape() {
        String dataGeracao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return "--------------------------------------------------------------------------------\n" +
               String.format("| Gerado em: %-60s |\n", dataGeracao) +
               "================================================================================\n";
    }
}