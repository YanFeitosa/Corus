
package teste;

import facade.FacadeSingletonController;
import infra.ArquivoRepositorioFactory;
import infra.RepositorioFactory;
import utils.ExcecoesRepositorio;
import entidade.Usuario; 

public class TesteFactoryAndTemplatePositivo {
    public void executarTestes() {
        System.out.println("\n--- Testando Novas Funcionalidades (Abstract Factory, Template Method) ---");

        RepositorioFactory factory = new ArquivoRepositorioFactory();

        try {
            FacadeSingletonController.resetInstance();
            FacadeSingletonController facade = FacadeSingletonController.getInstance(factory);

            // Nomes de utilizador válidos (sem números)
            String userUm = "testeum";
            String userDois = "testedois";

            // Limpando utilizadores de testes anteriores (se existirem)
            try { facade.removerUsuario(userUm); } catch (Exception e) { /* Ignora */ }
            try { facade.removerUsuario(userDois); } catch (Exception e) { /* Ignora */ }


            // PASSO 1: PREPARANDO DADOS
            System.out.println("\n[PASSO 1: PREPARANDO DADOS]");
            facade.cadastrarUsuario(userUm, "SenhaValida@1");
            facade.cadastrarUsuario(userDois, "SenhaValida@2");
            System.out.println("Utilizadores '" + userUm + "' e '" + userDois + "' cadastrados.");


            // PASSO 2: SIMULANDO LOGINS
            System.out.println("\n[PASSO 2: SIMULANDO LOGINS PARA GERAR ESTATÍSTICAS]");
            simularLogin(facade, userUm, "SenhaValida@1");
            simularLogin(facade, userUm, "SenhaValida@1"); // Segundo login para userUm
            simularLogin(facade, userDois, "SenhaValida@2");
            System.out.println("Logins simulados com sucesso.");


            // PASSO 3: GERANDO RELATÓRIOS
            System.out.println("\n[PASSO 3: GERANDO RELATÓRIOS COM TEMPLATE METHOD]");

            String relatorioPDF = facade.gerarRelatorioDeAcessos("pdf");
            System.out.println("\n--- INÍCIO RELATÓRIO PDF ---");
            System.out.println(relatorioPDF);
            System.out.println("--- FIM RELATÓRIO PDF ---");

            String relatorioHTML = facade.gerarRelatorioDeAcessos("html");
            System.out.println("\n--- INÍCIO RELATÓRIO HTML ---");
            System.out.println(relatorioHTML);
            System.out.println("--- FIM RELATÓRIO HTML ---");

        } catch (Exception e) {
            System.err.println("ERRO DURANTE O TESTE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método auxiliar que simula corretamente um login, atualizando as estatísticas.
     */
    private void simularLogin(FacadeSingletonController facade, String login, String senha) throws ExcecoesRepositorio {
        if (facade.verificarUsuario(login, senha)) {
            Usuario usuario = facade.buscarUsuario(login);
            if (usuario != null) {
                usuario.registrarAcesso();
                facade.atualizarUsuario(usuario);
            }
        }
    }
}