// utils/ExcecoesSenha.java
package utils;

import java.util.regex.Pattern;

public class ExcecoesSenha extends Exception {
    public ExcecoesSenha(String message) {
        super(message);
    }

    public static void validar(String senha, String login) throws ExcecoesSenha {
        // Regra 1: Comprimento mínimo de 8 caracteres
        if (senha == null || senha.length() < 8) {
            throw new ExcecoesSenha("Senha deve ter pelo menos 8 caracteres");
        }
        
        // Regra 2: Pelo menos uma letra maiúscula
        if (!Pattern.compile("[A-Z]").matcher(senha).find()) {
            throw new ExcecoesSenha("Senha deve conter pelo menos uma letra maiúscula");
        }
        
        // Regra 3: Pelo menos uma letra minúscula
        if (!Pattern.compile("[a-z]").matcher(senha).find()) {
            throw new ExcecoesSenha("Senha deve conter pelo menos uma letra minúscula");
        }
        
        // Regra 4: Pelo menos um número
        if (!Pattern.compile("[0-9]").matcher(senha).find()) {
            throw new ExcecoesSenha("Senha deve conter pelo menos um número");
        }
        
        // Regra 5: Pelo menos um caractere especial
        if (!Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>?]").matcher(senha).find()) {
            throw new ExcecoesSenha("Senha deve conter pelo menos um caractere especial: !@#$%^&*()_+-=[]{};':\",.<>?");
        }
        
        // Regra 6: Não pode conter espaços em branco
        if (senha.contains(" ")) {
            throw new ExcecoesSenha("Senha não pode conter espaços em branco");
        }
        
        // Regra 7: Não pode ser igual ao login
        if (login != null && senha.equals(login)) {
            throw new ExcecoesSenha("Senha não pode ser igual ao login");
        }
        
        // Regra 8: Não pode ser uma senha comum
        if (senha.matches("(?i)password|senha|12345678|qwertyui|abcdefgh")) {
            throw new ExcecoesSenha("Senha muito óbvia ou comprometida");
        }
    }
}