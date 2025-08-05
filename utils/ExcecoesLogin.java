// utils/ExcecoesLogin.java
package utils;

public class ExcecoesLogin extends Exception {
    public ExcecoesLogin(String message) {
        super(message);
    }

    public static void validar(String login) throws ExcecoesLogin {
        // Regra 1: Não pode ser vazio
        if (login == null || login.trim().isEmpty()) {
            throw new ExcecoesLogin("Login não pode ser vazio");
        }
        
        // Regra 2: Máximo de 12 caracteres
        if (login.length() > 12) {
            throw new ExcecoesLogin("Login deve ter no máximo 12 caracteres");
        }
        
        // Regra 3: Não pode conter números
        if (login.matches(".*\\d.*")) {
            throw new ExcecoesLogin("Login não pode conter números");
        }
    }
}