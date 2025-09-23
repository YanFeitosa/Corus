package controle;

import entidade.Usuario;
import java.util.List;

public class RelatorioHTML extends RelatorioTemplate {
    @Override
    protected String gerarCabecalho() {
        return "<html>\n<head>\n<title>Relatorio de Acessos</title>\n</head>\n<body>\n" +
                "<h1>Relatorio de Acesso de Usuarios</h1>\n" +
                "<table border='1'>\n" +
                "<tr><th>Login</th><th>Qtd. Acessos</th><th>Ultimo Login</th></tr>\n";
    }

    @Override
    protected String gerarCorpo(List<Usuario> usuarios) {
        StringBuilder corpo = new StringBuilder();
        for (Usuario u : usuarios) {
            corpo.append("<tr>\n<td>").append(u.getLogin()).append("</td>\n")
                    .append("<td>").append(u.getContagemDeAcessos()).append("</td>\n")
                    .append("<td>").append(u.getUltimoLoginFormatado()).append("</td>\n</tr>\n");
        }
        return corpo.toString();
    }

    @Override
    protected String gerarRodape() {
        return "</table>\n</body>\n</html>";
    }
}