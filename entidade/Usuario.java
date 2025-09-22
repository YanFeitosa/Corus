// Ficheiro: entidade/Usuario.java (CORRIGIDO)
package entidade;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Usuario {
    private String login;
    private String senha;
    private int contagemDeAcessos;
    private LocalDateTime ultimoLogin;

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
        this.contagemDeAcessos = 0;
        this.ultimoLogin = null;
    }

    public Usuario(String login, String senha, int contagemDeAcessos, LocalDateTime ultimoLogin) {
        this.login = login;
        this.senha = senha;
        this.contagemDeAcessos = contagemDeAcessos;
        this.ultimoLogin = ultimoLogin;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public int getContagemDeAcessos() {
        return contagemDeAcessos;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public String getUltimoLoginFormatado() {
        if (ultimoLogin == null) {
            return "Nunca acessou.";
        }
        return ultimoLogin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public void registrarAcesso() {
        this.contagemDeAcessos++;
        this.ultimoLogin = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Usuario{" + "login='" + login + '\'' + ", senha='" + senha + '\'' + '}';
    }
}