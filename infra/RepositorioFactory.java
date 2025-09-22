package infra;

public interface RepositorioFactory {
    UsuarioRepositorio createUsuarioRepositorio();

    DocumentoRepositorio createDocumentoRepositorio();
}