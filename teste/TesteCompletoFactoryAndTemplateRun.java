package teste;

public class TesteCompletoFactoryAndTemplateRun {
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("||      INICIANDO TESTES               ||");
        System.out.println("=========================================");

        // Executa os testes de cenário positivo
        TesteFactoryAndTemplatePositivo testesPositivos = new TesteFactoryAndTemplatePositivo();
        testesPositivos.executarTestes();

        // Executa os testes de validação (cenário negativo)
        TesteFactoryAndTemplateNegativo testesNegativos = new TesteFactoryAndTemplateNegativo();
        testesNegativos.executarTestes();

        System.out.println("\n=========================================");
        System.out.println("||       FIM DOS TESTES                  ||");
        System.out.println("===========================================");
    }
}
