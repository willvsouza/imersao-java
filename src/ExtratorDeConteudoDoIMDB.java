import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoDoIMDB implements ExtratorDeConteudo {

    public List<Conteudo> extraiConteudos(String json) throws FileNotFoundException {

        // pegar só os dados que interessam (título, poster e classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeAtributos = parser.parse(json);

        List<Conteudo> conteudos = new ArrayList<>();

        // popular a lista de conteúdos
        for (Map<String, String> atributos : listaDeAtributos) {

            // criei esta verificação pros casos de filmes que estávam sem um imDbRanting
            Double classificacao;
            if (atributos.get("imDbRating") == "") {
                classificacao = 0.0;;
            } else {
                classificacao = Double.parseDouble(atributos.get("imDbRating"));
            }

            String titulo = atributos.get("title");
            String urlImagem = atributos.get("image").replaceFirst("(@\\.)([0-9A-Z,_]+).jpg$", "");

            // altera o textoPoster de acordo com a classificação do ImDBRanting
            String textoPoster;
            InputStream imagemPessoa;
            if (classificacao >= 8.0) {
                textoPoster = "FILME TOP!";
                imagemPessoa = new FileInputStream(new File("sobreposicao/top.png"));
            } else if (classificacao >= 6.0) {
                textoPoster = "FILME BOM";
                imagemPessoa = new FileInputStream(new File("sobreposicao/bom.png"));
            } else {
                textoPoster = "MELHOR ESCOLHER OUTRO...";
                imagemPessoa = new FileInputStream(new File("sobreposicao/ruim.png"));
            }

            Conteudo conteudo = new Conteudo(titulo, urlImagem, textoPoster, imagemPessoa);

            conteudos.add(conteudo);
        }

        return conteudos;
    }
}
