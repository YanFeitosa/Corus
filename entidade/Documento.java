package entidade;

public class Documento {
    private String nome;
    private int tamanho;
    private String usuarioAssociado;

    public Documento(String nome, int tamanho, String usuarioAssociado) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.usuarioAssociado = usuarioAssociado;
    }

    public String getNome() {
        return nome;
    }

    public int getTamanho() {
        return tamanho;
    }

    public String getUsuarioAssociado() {
        return usuarioAssociado;
    }

    @Override
    public String toString() {
        return "Documento{" +
                "nome='" + nome + '\'' +
                ", tamanho=" + tamanho +
                ", usuarioAssociado='" + usuarioAssociado + '\'' +
                '}';
    }
}