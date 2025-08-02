package teste;

import entidade.Usuario;
import infra.PersistenciaArquivo;
import infra.PersistenciaMemoria;

import java.util.List;

public class TestePersistencia {

    public void testePersistenciaArquivo() {
        try {
            System.out.println("\n-- Testando Persistência em Arquivo --");
            PersistenciaArquivo repoArquivo = new PersistenciaArquivo();

            // Limpar arquivo
            List<Usuario> usuariosAntes = repoArquivo.listar();
            for (Usuario u : usuariosAntes) {
                repoArquivo.remover(u.getLogin());
            }

            Usuario u1 = new Usuario("ruan", "senha123");
            Usuario u2 = new Usuario("ana", "senha456");

            repoArquivo.adicionar(u1);
            repoArquivo.adicionar(u2);

            // Listar usuários
            List<Usuario> usuariosArquivo = repoArquivo.listar();
            for (Usuario u : usuariosArquivo) {
                System.out.println("Login: " + u.getLogin() + " | Senha: " + u.getSenha());
            }

            // Buscar usuário
            Usuario buscado = repoArquivo.buscar("ruan");
            System.out.println("Usuário buscado: " + (buscado != null ? buscado.getLogin() : "não encontrado"));

            // Remover usuário
            repoArquivo.remover("ruan");
            System.out.println("Removido usuário 'ruan'");

            Usuario removido = repoArquivo.buscar("ruan");
            System.out.println("Usuário 'ruan' após remoção: " + (removido == null ? "não encontrado" : "encontrado"));

        } catch (Exception e) {
            System.err.println("Erro no testePersistenciaArquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void testePersistenciaMemoria() {
        try {
            System.out.println("\n-- Testando Persistência em Memória --");
            PersistenciaMemoria repoMemoria = new PersistenciaMemoria();

            Usuario u1 = new Usuario("ruan", "senha123");
            Usuario u2 = new Usuario("ana", "senha456");

            repoMemoria.adicionar(u1);
            repoMemoria.adicionar(u2);

            List<Usuario> usuariosMemoria = repoMemoria.listar();
            for (Usuario u : usuariosMemoria) {
                System.out.println("Login: " + u.getLogin() + " | Senha: " + u.getSenha());
            }

            Usuario buscado = repoMemoria.buscar("ana");
            System.out.println("Usuário buscado: " + (buscado != null ? buscado.getLogin() : "não encontrado"));

            repoMemoria.remover("ruan");
            System.out.println("Removido usuário 'ruan'");

            Usuario removido = repoMemoria.buscar("ruan");
            System.out.println("Usuário 'ruan' após remoção: " + (removido == null ? "não encontrado" : "encontrado"));

        } catch (Exception e) {
            System.err.println("Erro no testePersistenciaMemoria: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
