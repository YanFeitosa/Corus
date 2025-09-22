// Crie o novo arquivo no pacote teste: TesteValidacoes.java
package teste;

import facade.FacadeSingletonController;
import infra.MemoriaRepositorioFactory;
import infra.RepositorioFactory;
import utils.ExcecoesLogin;
import utils.ExcecoesRepositorio;
import utils.ExcecoesSenha;

public class TesteFactoryAndTemplateNegativo {

    public void executarTestes() {
        System.out.println("\n--- Iniciando Testes de Validação (Cenários Negativos) ---");

        // PARA LIMPAR O SINGLETON
        FacadeSingletonController.resetInstance();

        // Agora, a linha abaixo vai criar uma NOVA instância com a fábrica de memória
        RepositorioFactory factory = new MemoriaRepositorioFactory();
        FacadeSingletonController facade = FacadeSingletonController.getInstance(factory);

        testeLoginInvalido(facade);
        testeSenhaInvalida(facade);
        testeUsuarioDuplicado(facade);

        System.out.println("\n--- Fim dos Testes de Validação ---");
    }

    private void testeLoginInvalido(FacadeSingletonController facade) {
        System.out.println("\n[TESTE] Tentando cadastrar com login inválido (com números)...");
        try {
            facade.cadastrarUsuario("login123", "SenhaValida@1");
            System.out.println(">>> FALHOU: O sistema não deveria ter permitido o cadastro.");
        } catch (ExcecoesLogin e) {
            System.out.println(">>> SUCESSO: Exceção esperada capturada: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(">>> FALHOU: Uma exceção inesperada foi lançada: " + e.getClass().getName());
        }
    }

    private void testeSenhaInvalida(FacadeSingletonController facade) {
        System.out.println("\n[TESTE] Tentando cadastrar com senha inválida (curta)...");
        try {
            facade.cadastrarUsuario("loginvalido", "senha");
            System.out.println(">>> FALHOU: O sistema não deveria ter permitido o cadastro.");
        } catch (ExcecoesSenha e) {
            System.out.println(">>> SUCESSO: Exceção esperada capturada: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(">>> FALHOU: Uma exceção inesperada foi lançada: " + e.getClass().getName());
        }
    }

    private void testeUsuarioDuplicado(FacadeSingletonController facade) {
        System.out.println("\n[TESTE] Tentando cadastrar um usuário que já existe...");
        try {
            // Primeiro, cadastra um usuário válido
            facade.cadastrarUsuario("usuariounico", "SenhaValida@1");
            System.out.println("Usuário 'usuariounico' cadastrado para o teste.");

            // Agora, tenta cadastrar de novo
            facade.cadastrarUsuario("usuariounico", "OutraSenha@123");
            System.out.println(">>> FALHOU: O sistema não deveria ter permitido o cadastro duplicado.");
        } catch (ExcecoesRepositorio e) {
            System.out.println(">>> SUCESSO: Exceção esperada capturada: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(">>> FALHOU: Uma exceção inesperada foi lançada: " + e.getClass().getName());
        }
    }
}