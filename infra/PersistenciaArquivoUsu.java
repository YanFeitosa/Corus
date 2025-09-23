package infra;

import entidade.Usuario;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import utils.ExcecoesRepositorio;

public class PersistenciaArquivoUsu implements UsuarioRepositorio {
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
                // Previne erro se o arquivo tiver linhas em branco
                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] dados = linha.split(";", -1);

                if (dados.length >= 4) {
                    String login = dados[0];
                    String senha = dados[1];
                    int contadorAcessos = Integer.parseInt(dados[2]);

                    LocalDateTime ultimoLogin = null;
                    if (dados[3] != null && !dados[3].trim().isEmpty() && !"null".equalsIgnoreCase(dados[3])) {
                        ultimoLogin = LocalDateTime.parse(dados[3]);
                    }

                    usuarios.add(new Usuario(login, senha, contadorAcessos, ultimoLogin));
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new ExcecoesRepositorio("Erro na leitura do arquivo de usuários: " + e.getMessage());
        }
        return usuarios;
    }

    private void salvarArquivo(List<Usuario> usuarios) throws ExcecoesRepositorio {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Usuario u : usuarios) {
                // Acessa o objeto LocalDateTime. Se for nulo, usa a string "null".
                // Caso contrário, usa a representação padrão de string do objeto.
                Object ultimoLoginObj = u.getUltimoLogin();
                String ultimoLoginStr = (ultimoLoginObj == null) ? "null" : ultimoLoginObj.toString();

                String linha = u.getLogin() + ";" +
                        u.getSenha() + ";" +
                        u.getContagemDeAcessos() + ";" +
                        ultimoLoginStr;

                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new ExcecoesRepositorio("Erro na gravação do ficheiro de usuários: " + e.getMessage());
        }
    }

    @Override
    public void atualizar(Usuario usuario) throws ExcecoesRepositorio {
        try {
            // 1. Lê todos os usuários do arquivo para a memória
            List<Usuario> usuarios = listar();

            // 2. Remove o usuário antigo
            boolean removido = usuarios.removeIf(u -> u.getLogin().equals(usuario.getLogin()));

            if (!removido) {
                throw new ExcecoesRepositorio("Usuário não encontrado para atualização no arquivo");
            }

            // 3. Adiciona a nova versão do usuário
            usuarios.add(usuario);

            // 4. Salva a lista inteira de volta no arquivo, sobrescrevendo o conteúdo
            salvarArquivo(usuarios);
        } catch (ExcecoesRepositorio e) {
            throw e;
        } catch (Exception e) {
            throw new ExcecoesRepositorio("Erro ao atualizar usuário no arquivo: " + e.getMessage());
        }
    }
}