package teste;

public class TestePersistenciaRun {
    public static void main(String[] args) {
        TestePersistencia teste = new TestePersistencia();

        System.out.println("=== Início dos testes ===");

        teste.testePersistenciaArquivo();
        teste.testePersistenciaMemoria();

        System.out.println("=== Fim dos testes ===");
    }
}