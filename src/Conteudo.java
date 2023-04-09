import java.io.InputStream;

public class Conteudo {

    private String titulo;
    private String urlImagem;
    private String texto;
    private InputStream imagem;

    public Conteudo(String titulo, String urlImagem, String texto, InputStream imagem) {
        this.titulo = titulo;
        this.urlImagem = urlImagem;
        this.texto = texto;
        this.imagem = imagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public String getTexto() {
        return texto;
    }

    public InputStream getImagem() {
        return imagem;
    }
}