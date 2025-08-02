package infra;

import entidade.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaArquivo implements UsuarioRepositorio {
    private final String caminhoArquivo = "usuarios.txt";

    @Override
    public void adicionar(Usuario usuario) throws Exception {
        List<Usuario> usuarios = listar();
        for (Usuario u : usuarios) {
            if (u.getLogin().equals(usuario.getLogin())) {
                // throw new ExcecoesRepositorio("Usuário já existe no arquivo.");
                System.out.println("Usuário já existe no arquivo.");
            }
        }
        usuarios.add(usuario);
        salvarArquivo(usuarios);
    }

    @Override
    public void remover(String login) throws Exception {
        List<Usuario> usuarios = listar();
        boolean removido = usuarios.removeIf(u -> u.getLogin().equals(login));
        if (!removido) {
            // throw new ExcecoesRepositorio("Usuário não encontrado no arquivo.");
            System.out.println("Usuário não encontrado no arquivo.");
        }
        salvarArquivo(usuarios);
    }

    @Override
    public Usuario buscar(String login) throws Exception {
        for (Usuario u : listar()) {
            if (u.getLogin().equals(login)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> listar() throws Exception {
        List<Usuario> usuarios = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            return usuarios;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 2) {
                    usuarios.add(new Usuario(dados[0], dados[1]));
                }
            }
        }
        return usuarios;
    }

    private void salvarArquivo(List<Usuario> usuarios) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Usuario u : usuarios) {
                bw.write(u.getLogin() + ";" + u.getSenha());
                bw.newLine();
            }
        }
    }
}
