package controle;

import entidade.Usuario;
import entidade.Documento;
import infra.UsuarioRepositorio;
import infra.DocumentoRepositorio;
import utils.ExcecoesLogin;
import utils.ExcecoesRepositorio;
import utils.ExcecoesSenha;

import java.util.List;

public class SistemaFacade {
    private GerenciamentoUsuario gerenciamentoUsuario;
    private GerenciamentoDocumento gerenciamentoDocumento;

    public SistemaFacade(UsuarioRepositorio usuarioRepositorio, DocumentoRepositorio documentoRepositorio) {
        this.gerenciamentoUsuario = new GerenciamentoUsuario(usuarioRepositorio);
        this.gerenciamentoDocumento = new GerenciamentoDocumento(documentoRepositorio, usuarioRepositorio);
    }

    // ---- Métodos relacionados a usuário ----
    public void cadastrarUsuario(String login, String senha) 
            throws ExcecoesRepositorio, ExcecoesLogin, ExcecoesSenha {
        gerenciamentoUsuario.adicionarUsuario(login, senha);
    }

    public List<Usuario> listarUsuarios() throws ExcecoesRepositorio {
        return gerenciamentoUsuario.listarUsuarios();
    }

    // ---- Métodos relacionados a documentos ----
    public void cadastrarDocumento(String nome, int tamanho, String usuarioAssociado) 
            throws ExcecoesRepositorio {
        gerenciamentoDocumento.adicionarDocumento(nome, tamanho, usuarioAssociado);
    }

    public List<Documento> listarDocumentos() throws ExcecoesRepositorio {
        return gerenciamentoDocumento.listarDocumentos();
    }
}
