package infra;

import entidade.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import utils.ExcecoesRepositorio;

public class PersistenciaArquivo implements UsuarioRepositorio {
    private final String caminhoArquivo = "usuarios.txt";

    @Override
    public void adicionar(Usuario usuario) throws ExcecoesRepositorio {
        try {
            List<Usuario> usuarios = listar();
            if (buscar(usuario.getLogin()) != null) {
                throw new ExcecoesRepositorio("Usuário já existe");
            }
            usuarios.add(usuario);
            salvarArquivo(usuarios);
        } catch (ExcecoesRepositorio e) {
            throw e;
        } catch (Exception e) {
            throw new ExcecoesRepositorio("Erro ao adicionar: " + e.getMessage());
        }
    }

    @Override
    public void remover(String login) throws ExcecoesRepositorio {
        try {
            List<Usuario> usuarios = listar();
            boolean removido = usuarios.removeIf(u -> u.getLogin().equals(login));
            if (!removido) {
                throw new ExcecoesRepositorio("Usuário não encontrado no arquivo");
            }
            salvarArquivo(usuarios);
        } catch (ExcecoesRepositorio e) {
            throw e;
        } catch (Exception e) {
            throw new ExcecoesRepositorio("Erro ao remover: " + e.getMessage());
        }
    }

    @Override
    public Usuario buscar(String login) throws ExcecoesRepositorio {
        try {
            for (Usuario u : listar()) {
                if (u.getLogin().equals(login)) {
                    return u;
                }
            }
            return null;
        } catch (Exception e) {
            throw new ExcecoesRepositorio("Erro ao buscar: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> listar() throws ExcecoesRepositorio {
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
        } catch (IOException e) {
            throw new ExcecoesRepositorio("Erro na leitura: " + e.getMessage());
        }
        return usuarios;
    }

    private void salvarArquivo(List<Usuario> usuarios) throws ExcecoesRepositorio {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Usuario u : usuarios) {
                bw.write(u.getLogin() + ";" + u.getSenha());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new ExcecoesRepositorio("Erro na gravação: " + e.getMessage());
        }
    }
}