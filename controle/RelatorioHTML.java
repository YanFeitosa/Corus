package controle;

import entidade.Relatorio;
import java.util.List;

public class RelatorioHTML extends RelatorioTemplate {
    @Override
    protected String gerarCabecalho() {
        return "<html>\n<head>\n<title>Relatorio de Acessos</title>\n<style>\n" +
                "table { border-collapse: collapse; width: 100%; }\n" +
                "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }\n" +
                "th { background-color: #f2f2f2; }\n" +
                ".inativo { color: #ff4444; }\n" +
                ".ativo { color: #00aa00; }\n" +
                "</style>\n</head>\n<body>\n" +
                "<h1>Relatorio de Acesso de Usuarios</h1>\n" +
                "<table>\n" +
                "<tr><th>Login</th><th>Qtd. Acessos</th><th>Status</th><th>Ultimo Login</th><th>Tipo</th></tr>\n";
    }

    @Override
    protected String gerarCorpo(List<Relatorio> relatorios) {
        StringBuilder corpo = new StringBuilder();
        for (Relatorio rel : relatorios) {
            String statusClass = rel.getQuantidadeAcessos() == 0 ? "inativo" : "ativo";
            
            corpo.append("<tr>\n")
                 .append("<td>").append(rel.getLogin()).append("</td>\n")
                 .append("<td>").append(rel.getQuantidadeAcessos()).append("</td>\n")
                 .append("<td class=\"").append(statusClass).append("\">")
                 .append(rel.getStatusAcesso()).append("</td>\n")
                 .append("<td>").append(rel.getUltimoAcessoFormatado()).append("</td>\n")
                 .append("<td>").append(rel.getTipoUsuario()).append("</td>\n")
                 .append("</tr>\n");
        }
        return corpo.toString();
    }

    @Override
    protected String gerarRodape() {
        return "</table>\n</body>\n</html>";
    }
}