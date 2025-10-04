package entidade;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa um relatório de acesso de usuários.
 * Contém os dados necessários para geração de relatórios formatados.
 */
public class Relatorio {
    private String login;
    private int quantidadeAcessos;
    private LocalDateTime ultimoAcesso;
    private String tipoUsuario;

    public Relatorio(String login, int quantidadeAcessos, LocalDateTime ultimoAcesso, String tipoUsuario) {
        this.login = login;
        this.quantidadeAcessos = quantidadeAcessos;
        this.ultimoAcesso = ultimoAcesso;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters
    public String getLogin() {
        return login;
    }

    public int getQuantidadeAcessos() {
        return quantidadeAcessos;
    }

    public LocalDateTime getUltimoAcesso() {
        return ultimoAcesso;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * Retorna a data formatada do último acesso
     */
    public String getUltimoAcessoFormatado() {
        if (ultimoAcesso == null) {
            return "Nunca acessou";
        }
        return ultimoAcesso.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    /**
     * Retorna uma descrição do status baseado na quantidade de acessos
     */
    public String getStatusAcesso() {
        if (quantidadeAcessos == 0) {
            return "Inativo";
        } else if (quantidadeAcessos <= 5) {
            return "Usuário Ocasional";
        } else if (quantidadeAcessos <= 20) {
            return "Usuário Ativo";
        } else {
            return "Usuário Muito Ativo";
        }
    }

    @Override
    public String toString() {
        return String.format("Relatorio{login='%s', acessos=%d, ultimoAcesso=%s, tipo=%s}",
                login, quantidadeAcessos, getUltimoAcessoFormatado(), tipoUsuario);
    }
}