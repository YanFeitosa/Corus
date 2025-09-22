package auth;

import utils.ExcecoesLogin;
import utils.ExcecoesRepositorio;
import utils.ExcecoesSenha;

public interface Authenticator {
    boolean authenticate(String usuario, String senha) throws ExcecoesLogin, ExcecoesSenha, ExcecoesRepositorio;
    String getTipoUsuario();
    void redirecionarParaUI();
}