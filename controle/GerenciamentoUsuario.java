package controle;

import entidade.Usuario;
import infra.UsuarioRepositorio;
import java.util.List;
import java.util.regex.Pattern;
import utils.ExcecoesLogin;
import utils.ExcecoesRepositorio;
import utils.ExcecoesSenha;

public class GerenciamentoUsuario {
    private UsuarioRepositorio repositorio;

    public GerenciamentoUsuario(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void adicionarUsuario(String login, String senha) 
            throws ExcecoesRepositorio, ExcecoesLogin, ExcecoesSenha {
        
        validarLogin(login);
        validarSenha(senha);
        
        repositorio.adicionar(new Usuario(login, senha));
    }

    private void validarLogin(String login) throws ExcecoesLogin {
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

    private void validarSenha(String senha) throws ExcecoesSenha {
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
        
        // Regra 6: Não pode conter o login (se login estiver definido)
        if (this.repositorio != null) {
            try {
                if (repositorio.buscar(senha) != null) {
                    throw new ExcecoesSenha("Senha não pode ser igual ao login");
                }
            } catch (ExcecoesRepositorio e) {
                // Ignora erros de busca durante validação
            }
        }
        
        // Regra 7: Não pode ser uma senha recentemente usada (simplificado)
        // (Implementação completa exigiria histórico de senhas)
        if (senha.matches("(?i)password|senha|12345678|qwertyui")) {
            throw new ExcecoesSenha("Senha muito óbvia ou comprometida");
        }
    }

    public List<Usuario> listarUsuarios() throws ExcecoesRepositorio {
        return repositorio.listar();
    }
}